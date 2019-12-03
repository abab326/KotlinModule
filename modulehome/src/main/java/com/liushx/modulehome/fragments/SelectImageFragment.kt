package com.liushx.modulehome.fragments

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.provider.MediaStore
import android.util.Base64
import com.liushx.corelibrary.IMyAidlInterface
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import kotlinx.android.synthetic.main.fragment_select_image.*

import com.liushx.modulehome.R
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType

import com.tbruyelle.rxpermissions2.RxPermissions
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.engine.impl.PicassoEngine


class SelectImageFragment : BaseFragment<MvpView, MvpPresenter<MvpView,*>>() {
    override fun createPresenter(): MvpPresenter<MvpView,*>? {
        return null
    }

    // AIDL 服务
    private var iMyAidlInterface: IMyAidlInterface? = null

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service)

        }

    }

    override fun setContextView(): Int {
        return R.layout.fragment_select_image

    }

    override fun initView() {
        super.initView()
        bindAIDLService()
    }

    override fun onDestroy() {
        super.onDestroy()
        context?.unbindService(serviceConnection)
    }

    override fun initEvent() {
        super.initEvent()


        selectImage_btn_select.setOnClickListener {
            selectImage()
        }
        btn_get_aidl.setOnClickListener {
            tv_show_aidl.text = iMyAidlInterface?.name ?: "没有建立联接"
        }
    }

    /**
     * 绑定服务
     *
     */
    fun bindAIDLService() {
        val intent = Intent()
        intent.action = "com.liushx.moduleuser.ImpMyAidlInterface"
        intent.setPackage(context?.packageName)
        context?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)

    }

    /**
     * 选择图片
     */
    fun selectImage() {

        val disposable = RxPermissions(this)
            .request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .subscribe {
                if (it) {
                    Matisse.from(this)
                        .choose(MimeType.ofAll())
                        .theme(R.style.SelectImageStyle)
                        .countable(false)
                        .maxSelectable(9)
                        .originalEnable(true)
                        .maxOriginalSize(10)
                        .imageEngine(GlideEngine())
                        .forResult(100)

                }
            }


    }
}