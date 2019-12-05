package com.liushx.moduleuser

import androidx.navigation.fragment.findNavController
import com.alibaba.android.arouter.facade.annotation.Route
import com.liushx.corelibrary.base.BaseActivity
import com.liushx.moduleuser.configs.UserARouterConfig
import kotlinx.android.synthetic.main.activity_user_main.*

@Route(path = UserARouterConfig.ViewPath.pathUserMain)
internal class UserMainActivity : BaseActivity() {
    override fun setContentViewById(): Int {
        return R.layout.activity_user_main
    }

    override fun initView() {
        super.initView()
    }

    override fun onNavigateUp() = user_main_navHostFragment.findNavController().navigateUp()
}
