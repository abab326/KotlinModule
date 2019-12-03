package com.liushx.moduleuser.services

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.liushx.corelibrary.config.BaseARouterConfig
import com.liushx.corelibrary.services.IUserModuleService
import com.liushx.moduleuser.configs.UserARouterConfig

@Route(path = BaseARouterConfig.Service.pathUserModuleService)
class ImpILoginService : IUserModuleService {

    private var isLogined: Boolean = false

    override fun init(context: Context?) {

    }

    override fun isLogined(): Boolean {
        return isLogined
    }

    override fun openUserModule() {
        ARouter.getInstance()
            .build(UserARouterConfig.ViewPath.pathUserMain)
            .withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .navigation()
    }

    fun setLoginStatus(isLogined: Boolean) {
        this.isLogined = isLogined
    }
}