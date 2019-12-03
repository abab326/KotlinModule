package com.liushx.modulehome.fragments

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.liushx.corelibrary.base.BaseFragment
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.modulehome.R

import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment<MvpView, MvpPresenter<MvpView,*>>() {
    override fun createPresenter(): MvpPresenter<MvpView,*>? {
        return null
    }

    companion object {
        fun newInstance(name: String): HomeFragment {
            val homeFragment = HomeFragment()
            val bundle = Bundle()
            bundle.putString("name", name)
            homeFragment.arguments = bundle
            return homeFragment
        }
    }

    override fun setContextView(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        super.initView()
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(mRootView?.findViewById(R.id.base_toolbar)!!)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = ""

    }

    override fun initData() {
        super.initData()

        textView.text = arguments?.getString("name") ?: ""
        mRootView?.findViewById<TextView>(R.id.base_toolbar_tv_title)!!.text =
            arguments?.getString("name") ?: ""
    }
}