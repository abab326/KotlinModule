package com.liushx.corelibrary.utils

import android.content.Context
import android.content.res.Resources
import android.telephony.PhoneNumberUtils
import android.text.format.DateUtils
import android.util.SparseArray
import android.util.TypedValue

/**
 * 单位转换类 dp px sp
 */
class DisplayUtils {

    companion object {
        /**
         * dp 转 px
         *
         */
        fun dpToPx(dpValue: Float): Float {

            val density = Resources.getSystem().displayMetrics.density
            return dpValue * density + 0.5f
        }

        /**
         * px 转 dp
         */
        fun pxToDp(pxValue: Float): Float {
            val density = Resources.getSystem().displayMetrics.density
            return pxValue / density + 0.5f
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         *
         */
        fun px2sp(pxValue: Float): Float {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return pxValue / fontScale + 0.5f
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         */
        fun sp2px(spValue: Float): Float {
            val fontScale = Resources.getSystem().displayMetrics.scaledDensity
            return spValue * fontScale + 0.5f
        }
    }
}