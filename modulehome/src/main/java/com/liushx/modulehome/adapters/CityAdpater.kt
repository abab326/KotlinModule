package com.liushx.modulehome.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.liushx.modulehome.R
import com.liushx.modulehome.models.CityBean

class CityAdpater : BaseQuickAdapter<CityBean, BaseViewHolder>(R.layout.item_home_demo) {


    override fun convert(helper: BaseViewHolder, item: CityBean?) {
        helper.setText(R.id.textView2, item?.name ?: "")
            .addOnClickListener(R.id.textView2)

    }
}
