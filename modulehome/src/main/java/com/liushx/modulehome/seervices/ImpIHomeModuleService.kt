package com.liushx.modulehome.seervices

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.liushx.corelibrary.config.BaseARouterConfig
import com.liushx.corelibrary.services.IHomeModuleService
import com.liushx.modulehome.configs.HomeARouterConfig

@Route(path = BaseARouterConfig.Service.pathHomeModuleService)
class ImpIHomeModuleService : IHomeModuleService {
    /**
     * 打开首页模块
     *
     */
    override fun openHomeModule() {
        ARouter.getInstance()
            .build(HomeARouterConfig.ViewPath.pathHomeMain)
            .withTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            .navigation()
    }

    override fun init(context: Context?) {

    }
}