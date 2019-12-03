package com.liushx.corelibrary.mvp

import androidx.lifecycle.*
import com.orhanobut.logger.Logger

/**
 * MvpPresenter
 *
 * MVP 框架开发 presenter 基础类 感知activity 或fragment 生命周期变化
 *
 */
abstract class MvpPresenter<V : MvpView, M : ViewModel>(protected var mvpView: V) :
    LifecycleObserver {
    protected open var mViewModel: M? = null

    init {
        this.mvpView.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate() {
        Logger.d("${this.javaClass.simpleName} : onCreate")
        mViewModel = createViewModel()
    }

    abstract fun createViewModel(): M?

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    open fun onStart() {
        Logger.d("${this.javaClass.simpleName} : onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    open fun onStop() {
        Logger.d("${this.javaClass.simpleName} : onStop")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    open fun onResume() {
        Logger.d("${this.javaClass.simpleName} : onResume")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    open fun onPause() {
        Logger.d("${this.javaClass.simpleName} : onPause")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy() {
        Logger.d("${this.javaClass.simpleName} : onDestroy")
        this.mvpView.lifecycle.removeObserver(this)
    }
}