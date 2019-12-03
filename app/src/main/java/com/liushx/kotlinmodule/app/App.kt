package com.liushx.kotlinmodule.app

import android.app.Application
import com.liushx.corelibrary.base.BaseApplication

class App : BaseApplication() {


    override fun initModuleApp(application: Application) {
        for (appClassName in AppConfig.apps) {
            try {
                val name = Class.forName(appClassName)
                val baseApp = name.newInstance() as BaseApplication
                baseApp.initModuleApp(application)
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            }

        }
    }

}