package com.liushx.modulehome.models

import com.mcxtzhang.indexlib.IndexBar.bean.BaseIndexPinyinBean

class CityBean : BaseIndexPinyinBean {
    var name: String = ""

    constructor() : super()
    constructor(name: String) : super() {
        this.name = name
    }


    override fun getTarget(): String {
        return name
    }

    override fun isNeedToPinyin(): Boolean {
        return true
    }

    override fun isShowSuspension(): Boolean {
        return true
    }
}
