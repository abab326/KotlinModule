package com.liushx.corelibrary.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.view.View

/**
 *  view 转bitmap
 *
 */
class ViewToDrawableUtil {

    companion object {
        /**
         * save view as a bitmap
         */
        private fun saveViewBitmap(view: View): Bitmap? {
            // get current view bitmap
            view.isDrawingCacheEnabled = true
            view.buildDrawingCache(true)
            var bitmap = view.getDrawingCache(true)

            val bmp = duplicateBitmap(bitmap)
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
                bitmap = null
            }
            // clear the cache
            view.isDrawingCacheEnabled = false
            return bmp
        }


        fun duplicateBitmap(bmpSrc: Bitmap?): Bitmap? {
            if (null == bmpSrc) {
                return null
            }

            val bmpSrcWidth = bmpSrc.width
            val bmpSrcHeight = bmpSrc.height

            val bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888)
            if (null != bmpDest) {
                val canvas = Canvas(bmpDest)
                val rect = Rect(0, 0, bmpSrcWidth, bmpSrcHeight)
                canvas.drawBitmap(bmpSrc, rect, rect, null)
            }

            return bmpDest
        }


        fun convertViewToBitmap(view: View): Bitmap {

            val bitmap = Bitmap.createBitmap(
                view.width, view.height,
                Bitmap.Config.ARGB_8888
            )
            //利用bitmap生成画布
            val canvas = Canvas(bitmap)
            //把view中的内容绘制在画布上
            view.draw(canvas)

            return bitmap
        }
    }
}