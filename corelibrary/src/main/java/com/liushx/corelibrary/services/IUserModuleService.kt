package com.liushx.corelibrary.services

import com.alibaba.android.arouter.facade.template.IProvider

/**
 * 用户组件 提供外部调用方法
 *
 */
interface IUserModuleService : IProvider {

    /**
     *
     * 当前是否已登录
     *
     */
    fun isLogined(): Boolean

    /**
     * 打开用户模块
     */
    fun openUserModule()


}