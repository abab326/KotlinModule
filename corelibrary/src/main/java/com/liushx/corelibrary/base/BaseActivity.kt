package com.liushx.corelibrary.base


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.liushx.corelibrary.R
import android.view.MenuItem
import com.liushx.corelibrary.mvp.MvpView
import kotlinx.android.synthetic.main.base_toolbar.*


/**
 * 应用中基本通用 activity 类
 *
 */
abstract class BaseActivity : AppCompatActivity(), MvpView {


    private var mToolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setContentViewById())
        initToolbar()
        initView()
        initData()
        initEvent()
    }


    override fun <T> getBundle(): T {

        return intent as T

    }

    /**
     * 初始化标题栏
     */
    private fun initToolbar() {

        mToolbar = findViewById(R.id.base_toolbar)

        if (mToolbar != null) {
            mToolbar?.title = ""
            setSupportActionBar(mToolbar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    /**
     * 设置标题
     * 参数 [title] String 类型的
     *
     */
    fun setToolBarTitle(title: String) {
        if (base_toolbar_tv_title != null) {
            base_toolbar_tv_title.text = title
        }
    }

    /**
     * 设置标题
     *
     * 参数 [titleId] String 资源ID
     *
     */
    fun setToolBarTitle(titleId: Int) {

        if (base_toolbar_tv_title != null) {
            base_toolbar_tv_title.setText(titleId)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * 设置布局文件
     */
    protected abstract fun setContentViewById(): Int

    protected open fun initView() {

    }

    protected open fun initData() {

    }

    protected open fun initEvent() {

    }
}