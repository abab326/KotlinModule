package com.liushx.corelibrary.callbacks

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.os.Looper
import com.liushx.corelibrary.manager.ActivityStackManager
import com.liushx.corelibrary.utils.ToastUtils
import com.orhanobut.logger.Logger
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

/**
 *
 * 自定义全局异常处理类
 *
 *  当有未处理的异常出现时，保存到本地文件
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    private lateinit var context: Application
    private val infos = TreeMap<String, Any>()

    private val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()


    private object Instance {
        val mInstance = CrashHandler()
    }

    companion object {

        fun getInstance(): CrashHandler {
            return Instance.mInstance
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {

        if (!handleException(e) && defaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(t, e)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
            }
            //退出程序
            ActivityStackManager.exitApp()
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(1)
        }
    }

    /**
     * 初始化
     */
    fun init(application: Application) {
        this.context = application
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private fun handleException(ex: Throwable): Boolean {
        //收集设备参数信息
        collectDeviceInfo(context)
        //保存日志文件
        saveCrashInfo2File(ex)
        //使用Toast来显示异常信息
        Thread {
            Looper.prepare()
            ToastUtils.showLong("很抱歉,程序出现异常,即将退出.")
            Looper.loop()
        }.start()

        return true
    }


    private fun collectDeviceInfo(mContext: Context) {
        try {
            val pm = mContext.packageManager
            val pi = pm.getPackageInfo(mContext.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = pi.versionName ?: "未知版本名称"
                infos["versionName"] = versionName
                infos["versionCode"] = pi.versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }
        val fields = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infos[field.name] = field.get(null).toString()
            } catch (e: Exception) {
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return  返回文件名称,便于将文件传送到服务器
     */
    @SuppressLint("SdCardPath")
    private fun saveCrashInfo2File(ex: Throwable): String? {

        val sb = StringBuffer()
        for (entry in infos.entries) {
            val key = entry.key
            val value = entry.value
            sb.append("$key=$value\n")
        }

        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        Logger.d(sb.toString())
        try {
            val timestamp = System.currentTimeMillis()
            val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
            val fileName = "crash-$time-$timestamp.log"
            if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                val path =
                    Environment.getExternalStorageDirectory().absolutePath + File.separator + "app_test_log"
                val dir = File(path)
                if (!dir.exists()) {
                    dir.mkdirs()
                }
                val fos = FileOutputStream(path + fileName)
                fos.write(sb.toString().toByteArray())
                fos.close()
            }
            return fileName
        } catch (e: Exception) {

        }
        return null
    }
}