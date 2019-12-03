package com.liushx.kotlinmodule

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.liushx.corelibrary.base.BaseActivity
import com.liushx.corelibrary.config.BaseARouterConfig
import com.liushx.corelibrary.services.IUserModuleService
import com.youth.banner.loader.ImageLoaderInterface
import kotlinx.android.synthetic.main.activity_app_main.*


class MainActivity : BaseActivity() {

    private val imageList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        super.onCreate(savedInstanceState)
    }

    override fun setContentViewById(): Int {
        return R.layout.activity_app_main
    }

    override fun initData() {
        super.initData()
        splash_banner.setImageLoader(GlideImageLoader())
        imageList.add("http://e.hiphotos.baidu.com/image/pic/item/10dfa9ec8a1363279e1ed28c9b8fa0ec09fac79a.jpg")
        imageList.add("http://b.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe09f291548961600c338644ad64.jpg")
        imageList.add("http://h.hiphotos.baidu.com/image/pic/item/50da81cb39dbb6fd653d4abb0324ab18962b37cc.jpg")
        imageList.add("http://a.hiphotos.baidu.com/image/pic/item/730e0cf3d7ca7bcbdaea9ab4b4096b63f724a89c.jpg")
        splash_banner.setImages(imageList)
        splash_banner.isAutoPlay(false)
        splash_banner.start()
    }

    override fun initEvent() {
        super.initEvent()
        splash_tv_skip.setOnClickListener {
            val iLoginService =
                ARouter.getInstance().build(BaseARouterConfig.Service.pathUserModuleService)
                    .navigation() as IUserModuleService?
            iLoginService?.openUserModule()
            finish()
        }
    }

    private inner class GlideImageLoader : ImageLoaderInterface<View> {
        override fun displayImage(context: Context?, path: Any?, imageView: View?) {
            Glide.with(context!!).load(path as String)
                .into(imageView!!.findViewById(R.id.item_splash_iv))
        }

        override fun createImageView(context: Context?): View {
            return LayoutInflater.from(context!!)
                .inflate(R.layout.item_layout_splash, null, false)
        }

    }
}
