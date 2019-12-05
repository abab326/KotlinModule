package com.liushx.moduleuser.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.alibaba.android.arouter.launcher.ARouter
import com.liushx.corelibrary.config.BaseARouterConfig
import com.liushx.corelibrary.utils.ToastUtils
import com.liushx.moduleuser.services.ImpILoginService

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val userName = MutableLiveData<String>("")
    private val passWord = MutableLiveData<String>("")

    private val isLogined = MutableLiveData<Boolean>(false)


    fun getUserName(): MutableLiveData<String> {
        return userName
    }

    fun getPassWord(): MutableLiveData<String> {
        return passWord
    }

    fun isLogined(): MutableLiveData<Boolean> {

        return isLogined
    }

    fun login() {
        if (userName.value == "") {
            ToastUtils.show("用户名不能为空")
            return
        }
        if (passWord.value == "") {
            ToastUtils.show("密码不能为空")
            return
        }
        val impILoginService =
            ARouter.getInstance().build(BaseARouterConfig.Service.pathUserModuleService).navigation() as ImpILoginService
        impILoginService.setLoginStatus(true)
        isLogined.value = true
    }
}