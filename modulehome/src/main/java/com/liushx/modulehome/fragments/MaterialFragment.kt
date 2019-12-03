package com.liushx.modulehome.fragments

import android.widget.SeekBar
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.modulehome.R
import kotlinx.android.synthetic.main.fragment_material.*

class MaterialFragment : BaseFragment<MvpView, MvpPresenter<MvpView, *>>() {
    override fun createPresenter(): MvpPresenter<MvpView, *>? {
        return null
    }

    override fun setContextView(): Int {
        return R.layout.fragment_material
    }


    override fun initView() {
        super.initView()
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
    }
}