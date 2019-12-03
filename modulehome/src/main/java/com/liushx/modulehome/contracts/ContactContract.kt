package com.liushx.modulehome.contracts

import com.liushx.corelibrary.mvp.MvpView

interface ContactContract {

    interface IView : MvpView {

    }

    interface IPresenter {

        fun setUserName(userName: String)

    }
}