package com.liushx.corelibrary.utils

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.Context

import java.io.File
import androidx.core.content.FileProvider
import android.os.Build
import android.content.Intent
import android.net.Uri
import java.security.MessageDigest


class AppUtils {
    companion object {
        /**
         * 得到软件版本号
         *
         * @param context 上下文
         * @return 当前版本Code
         */
        fun getVerCode(context: Context): Int {
            return try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionCode
            } catch (e: PackageManager.NameNotFoundException) {
                -1
            }
        }


        /**
         * 得到软件显示版本信息
         *
         * @param context 上下文
         * @return 当前版本信息
         */
        fun getVerName(context: Context): String {
            return try {
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            } catch (e: PackageManager.NameNotFoundException) {
                ""
            }
        }

        /**
         * 安装apk
         * Android 7.0 或更高版本的应用私有目录被限制访问
         * @param context 上下文
         * @param file    APK文件
         * @paras authority provider->authority属性
         */
        fun installApk(context: Context, file: File, authority: String) {
            val intent = Intent()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.action = Intent.ACTION_VIEW
            intent.setDataAndType(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                    FileProvider.getUriForFile(context, authority, file)
                else
                    Uri.fromFile(file), "\"application/vnd.android.package-archive\""
            )
            context.startActivity(intent)
        }

        /**
         * 获取应用签名
         *
         * @param context 上下文
         * @param pkgName 包名
         * @return 返回应用的签名
         */
        @SuppressLint("PackageManagerGetSignatures")
        fun getSign(context: Context, pkgName: String): String? {
            return try {
                val pis = context.packageManager
                    .getPackageInfo(pkgName, PackageManager.GET_SIGNATURES)
                hexdigest(pis.signatures[0].toByteArray())
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }

        }

        /**
         * 将签名字符串转换成需要的32位签名
         *
         * @param paramArrayOfByte 签名byte数组
         * @return 32位签名字符串
         */
        private fun hexdigest(paramArrayOfByte: ByteArray): String {
            val hexDigits = charArrayOf(
                48.toChar(),
                49.toChar(),
                50.toChar(),
                51.toChar(),
                52.toChar(),
                53.toChar(),
                54.toChar(),
                55.toChar(),
                56.toChar(),
                57.toChar(),
                97.toChar(),
                98.toChar(),
                99.toChar(),
                100.toChar(),
                101.toChar(),
                102.toChar()
            )
            try {
                val localMessageDigest = MessageDigest.getInstance("MD5")
                localMessageDigest.update(paramArrayOfByte)
                val arrayOfByte = localMessageDigest.digest()
                val arrayOfChar = CharArray(32)
                var i = 0
                var j = 0
                while (true) {
                    if (i >= 16) {
                        return String(arrayOfChar)
                    }
                    val k = arrayOfByte[i].toInt()
                    arrayOfChar[j] = hexDigits[0xF and k.ushr(4)]
                    arrayOfChar[++j] = hexDigits[k and 0xF]
                    i++
                    j++
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return ""
        }
    }
}