package com.liushx.corelibrary.base


import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ReportFragment
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.orhanobut.logger.Logger

/**
 * Fragment 基础类
 *
 */
abstract class BaseFragment<V : MvpView, P : MvpPresenter<V, *>> : Fragment(), MvpView {
    protected var mRootView: View? = null
    //是否初始化
    private var isInitView = false
    protected var mPresenter: P? = null

    init {
        mPresenter = this.createPresenter()
    }

    abstract fun createPresenter(): P?


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("${this.javaClass.simpleName} :onCreate")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Logger.d("${this.javaClass.simpleName} :onCreateView")
        if (mRootView == null)
            mRootView = inflater.inflate(setContextView(), container, false)
        val viewGroup = mRootView!!.parent as ViewGroup?
        viewGroup?.removeView(mRootView)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("${this.javaClass.simpleName} :onViewCreated")
        if (!isInitView) {
            initView()
            initData()
            initEvent()
            isInitView = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Logger.d("${this.javaClass.simpleName} :onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Logger.d("${this.javaClass.simpleName} :onStart")
    }

    override fun onStop() {
        super.onStop()
        Logger.d("${this.javaClass.simpleName} :onStop")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Logger.d("${this.javaClass.simpleName} :onSaveInstanceState")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("${this.javaClass.simpleName} :onDestroy")
    }


    override fun <T> getBundle(): T? {
        return arguments as T?
    }

    /**
     * 设置布局资源文件
     *  return 返回设置布局资源 id
     */
    protected abstract fun setContextView(): Int

    /**
     * 初始化视图
     *
     */
    protected open fun initView() {

    }

    /**
     * 初始 绑定数据
     */
    protected open fun initData() {

    }

    /**
     * 初始化事件
     */
    protected open fun initEvent() {

    }

}