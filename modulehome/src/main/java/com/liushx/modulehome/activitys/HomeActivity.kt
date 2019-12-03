package com.liushx.modulehome.activitys

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import com.liushx.corelibrary.IMyAidlInterface
import com.liushx.corelibrary.base.BaseActivity
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.modulehome.R
import com.liushx.modulehome.configs.HomeARouterConfig
import com.liushx.modulehome.fragments.ContactsFragment
import com.liushx.modulehome.fragments.HomeFragment
import com.liushx.modulehome.fragments.MaterialFragment
import com.liushx.modulehome.fragments.SelectImageFragment
import kotlinx.android.synthetic.main.activity_home.*


@Route(path = HomeARouterConfig.ViewPath.pathHomeMain)
internal class HomeActivity : BaseActivity() {
    private var fragments = ArrayList<BaseFragment<*, *>>()

    override fun setContentViewById(): Int {
        return R.layout.activity_home
    }

    private val iMyAidlInterface: IMyAidlInterface? = null
    override fun initView() {
        super.initView()

        home_bottom_navigation_view.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 4
            }

            override fun createFragment(position: Int): Fragment {
                return fragments[position]
            }

        }
        home_viewpager.adapter = adapter
        home_viewpager.offscreenPageLimit = 3
    }


    override fun initData() {
        super.initData()
        fragments.add(HomeFragment.newInstance("首页"))
        fragments.add(ContactsFragment())
        fragments.add(SelectImageFragment())
        fragments.add(MaterialFragment())

    }

    override fun initEvent() {
        super.initEvent()
        home_viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        home_viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 ->
                        home_bottom_navigation_view.selectedItemId = R.id.menu_home_01
                    1 ->
                        home_bottom_navigation_view.selectedItemId = R.id.menu_home_02
                    2 ->
                        home_bottom_navigation_view.selectedItemId = R.id.menu_home_03
                    3 ->
                        home_bottom_navigation_view.selectedItemId = R.id.menu_home_04
                }

            }
        })
        home_bottom_navigation_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home_01 -> {
                    home_viewpager.setCurrentItem(0, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_home_02 -> {
                    home_viewpager.setCurrentItem(1, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_home_03 -> {
                    home_viewpager.setCurrentItem(2, false)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_home_04 -> {
                    home_viewpager.setCurrentItem(3, false)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener true
            }
        }
    }


}