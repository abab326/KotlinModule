package com.liushx.moduleuser.fragments

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.config.BaseARouterConfig
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.corelibrary.services.IHomeModuleService
import com.liushx.corelibrary.utils.StatusBarUtil
import com.liushx.moduleuser.R
import com.liushx.moduleuser.viewmodels.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<MvpView, MvpPresenter<MvpView, LoginViewModel>>() {

    override fun createPresenter(): MvpPresenter<MvpView, LoginViewModel>? {
        return null
    }

    private var loginViewModel: LoginViewModel? = null


    override fun onStart() {
        super.onStart()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        StatusBarUtil.setStatusBarColor(activity!!.window, android.R.color.white, false)
    }

    override fun setContextView(): Int {
        return R.layout.fragment_login
    }

    override fun initData() {
        super.initData()
        loginViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application)
                .create(LoginViewModel::class.java)
        Glide
            .with(this)
            .load(R.mipmap.ic_launcher_round)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(login_iv_logo)

        loginViewModel?.isLogined()?.observe(this, Observer {
            if (it) {
                val homeModuleService =
                    ARouter.getInstance().build(BaseARouterConfig.Service.pathHomeModuleService)
                        .navigation() as IHomeModuleService?
                homeModuleService?.openHomeModule()
            }
        })
    }

    override fun initEvent() {
        super.initEvent()
        login_edt_user_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel?.getUserName()?.postValue(s.toString())
            }
        })
        login_edt_user_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                loginViewModel?.getPassWord()?.postValue(s.toString())
            }

        })
        login_btn_go.setOnClickListener {
            loginViewModel?.login()
        }
    }
}