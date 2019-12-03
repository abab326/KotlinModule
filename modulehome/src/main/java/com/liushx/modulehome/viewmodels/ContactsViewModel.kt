package com.liushx.modulehome.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class ContactsViewModel(application: Application) : AndroidViewModel(application) {
    private var name = MutableLiveData<String>("ViewModel")

    fun getName() = name


}