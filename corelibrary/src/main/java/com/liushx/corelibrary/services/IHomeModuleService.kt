package com.liushx.corelibrary.services

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 首页组件 提供外部调用方法
 *
 */
interface IHomeModuleService : IProvider {
    /**
     * 打开首页模块
     */
    fun openHomeModule()
}