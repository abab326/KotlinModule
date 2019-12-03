package com.liushx.corelibrary.base

import android.app.Application
import android.content.Context
import com.alibaba.android.arouter.launcher.ARouter
import com.liushx.corelibrary.BuildConfig
import com.liushx.corelibrary.callbacks.CrashHandler
import com.liushx.corelibrary.callbacks.ImplActivityLifecycleCallbacks
import com.liushx.corelibrary.utils.ToastUtil
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


abstract class BaseApplication : Application() {


    private lateinit var activityLifecycleCallbacks: ImplActivityLifecycleCallbacks

    companion object {
        private lateinit var application: BaseApplication
        fun getContext(): Context {
            return application.applicationContext
        }

        fun getInstance(): BaseApplication {
            return application
        }
    }

    override fun onCreate() {
        super.onCreate()
        application = this
        Logger.addLogAdapter(object : AndroidLogAdapter() {

            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        ToastUtil.init(this)
        ARouter.init(this)
        // 注册 Activity 生命周期监听
        this.activityLifecycleCallbacks = ImplActivityLifecycleCallbacks()
        registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks)
        // 初始化全局异常捕获
        CrashHandler.getInstance().init(this)
        // 初始化应用数据
        initModuleApp(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacks)
    }

    abstract fun initModuleApp(application: Application)
}