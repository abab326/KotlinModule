package com.liushx.modulehome.fragments

import android.widget.SeekBar
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.corelibrary.utils.AESUtils
import com.liushx.corelibrary.utils.RSAUtils
import com.liushx.modulehome.R
import kotlinx.android.synthetic.main.fragment_material.*
import java.security.KeyPair


class MaterialFragment : BaseFragment<MvpView, MvpPresenter<MvpView, *>>() {
    private var content = ""
    private var rsa_context: String = ""
    private lateinit var keyPair: HashMap<String, String>
    override fun createPresenter(): MvpPresenter<MvpView, *>? {
        return null
    }

    override fun setContextView(): Int {
        return R.layout.fragment_material


    }


    override fun initData() {
        super.initData()
        keyPair = RSAUtils.generateKeys()
    }

    override fun initEvent() {
        super.initEvent()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                circleDashboard.setProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }


        })
        btn_encrypt.setOnClickListener {
            content = AESUtils.aesEncrypt("我是一个中国人")
            tv_content.text = content
        }
        btn_decrypt.setOnClickListener {
            content = AESUtils.aesDecrypt(content)
            tv_content.text = content
        }


        btn_rsa_decrypt.setOnClickListener {
            rsa_context = RSAUtils.decryptPrivate(rsa_context, keyPair["private"]!!)
            tv_rsa_content.text = rsa_context
        }
        btn_rsa_encrypt.setOnClickListener {
            rsa_context = RSAUtils.encryptPublic("我是一个中国人", keyPair["public"]!!)
            tv_rsa_content.text = rsa_context
        }
    }
}