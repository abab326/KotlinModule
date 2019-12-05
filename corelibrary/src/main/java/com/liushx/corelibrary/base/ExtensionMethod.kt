package com.liushx.corelibrary.base

import android.app.Activity
import com.liushx.corelibrary.utils.ToastUtils

/************** Kotlin 扩展函数 ********************/


fun Activity.showToast(msg: String) {
    ToastUtils.show(msg)
}