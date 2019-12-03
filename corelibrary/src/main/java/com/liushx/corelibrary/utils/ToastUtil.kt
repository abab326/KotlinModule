package com.liushx.corelibrary.utils

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * Toast 工具类
 */
@SuppressLint("StaticFieldLeak")
class ToastUtil {
    companion object {
        private var mToast: Toast? = null

        fun init(content: Context) {
            if (mToast == null) {
                mToast = Toast.makeText(content, "", Toast.LENGTH_SHORT)
            }
        }

        fun show(message: String) {
            if (mToast != null && message.isNotEmpty()) {
                mToast?.setText(message)
                mToast?.duration = Toast.LENGTH_SHORT
                mToast?.show()
            }

        }

        fun show(messageId: Int) {
            if (mToast != null && messageId > 0) {
                mToast?.setText(messageId)
                mToast?.duration = Toast.LENGTH_SHORT
                mToast?.show()
            }

        }

        fun showLong(message: String) {
            if (mToast != null && message.isNotEmpty()) {
                mToast?.setText(message)
                mToast?.duration = Toast.LENGTH_LONG
                mToast?.show()
            }

        }

        fun showLong(messageId: Int) {
            if (mToast != null && messageId > 0) {
                mToast?.setText(messageId)
                mToast?.duration = Toast.LENGTH_LONG
                mToast?.show()
            }

        }
    }


}