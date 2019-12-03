package com.liushx.corelibrary.manager

import android.app.Activity
import java.util.*

/**
 * 应用全过程 activity 管理
 *
 */

object ActivityStackManager {

    private val activities = Stack<Activity>()

    fun addActivity(activity: Activity) {
        if (activities.search(activity) == -1) {
            activities.push(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        if (!activities.empty()) {
            activities.remove(activity)
        }
    }

    /**
     * 获取当前activity
     */
    fun getTopActivity(): Activity? {
        return if (activities.empty()) null else activities.peek()
    }

    /**
     * 退出应用
     */
    fun exitApp() {
        while (!activities.empty()) {
            val activity = activities.pop()
            activity.finish()
        }
    }
}