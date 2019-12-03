package com.liushx.modulehome.persenters

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.liushx.corelibrary.base.BaseApplication
import com.liushx.corelibrary.mvp.MvpPresenter
import com.liushx.corelibrary.mvp.MvpView
import com.liushx.modulehome.contracts.ContactContract
import com.liushx.modulehome.viewmodels.ContactsViewModel
import com.orhanobut.logger.Logger

class ContactsPresenter(mvpView: MvpView) : MvpPresenter<MvpView, ContactsViewModel>(mvpView),
    ContactContract.IPresenter {

    override fun createViewModel(): ContactsViewModel? {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(BaseApplication.getInstance())
            .create<ContactsViewModel>(ContactsViewModel::class.java)
    }

    override fun setUserName(userName: String) {
        mViewModel?.getName()!!.observe(mvpView, Observer<String> {
            Logger.d(it)
        })
    }
}