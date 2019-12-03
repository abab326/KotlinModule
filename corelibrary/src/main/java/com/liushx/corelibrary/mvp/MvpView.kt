package com.liushx.corelibrary.mvp

import androidx.lifecycle.LifecycleOwner

interface MvpView : LifecycleOwner {

    /**
     * 获取 activity 间或者activity 与fragment 传递参数
     */
    fun <T> getBundle(): T?
}