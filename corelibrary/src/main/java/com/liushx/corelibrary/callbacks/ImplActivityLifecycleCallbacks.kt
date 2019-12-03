package com.liushx.corelibrary.callbacks

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.liushx.corelibrary.manager.ActivityStackManager

/**
 *
 *  activity全生命周期管理
 *
 */
class ImplActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

    private var resumeCount: Int = 0

    // 应用是否在前台显示
    private var isForground = true


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        ActivityStackManager.addActivity(activity)

    }

    override fun onActivityStarted(activity: Activity) {
        resumeCount++
        isForground = true
    }

    override fun onActivityResumed(activity: Activity) {


    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        resumeCount--
        if (resumeCount <= 0) {
            isForground = false
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        ActivityStackManager.removeActivity(activity)

    }

    fun isForgroundAppValue(): Boolean {
        return isForground
    }
}