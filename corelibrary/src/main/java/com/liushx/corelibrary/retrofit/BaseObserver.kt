package com.liushx.corelibrary.retrofit

import android.app.Dialog
import android.app.ProgressDialog
import android.text.TextUtils
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AlertDialogLayout
import androidx.core.app.DialogCompat
import com.google.gson.JsonParseException
import com.liushx.corelibrary.R
import com.liushx.corelibrary.base.BaseApplication
import com.liushx.corelibrary.manager.ActivityStackManager
import com.liushx.corelibrary.utils.ToastUtil
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.json.JSONException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException
import java.text.ParseException

abstract class BaseObserver<T>(
    private var mShowLoading: Boolean = false
) : Observer<T> {

    companion object {
        /**
         * token失效 发送广播标识
         */
        const val TOKEN_INVALID_TAG = "token_invalid"
        const val QUIT_APP = "quit_app"

        const val CONNECT_ERROR = "网络连接失败,请检查网络"
        const val CONNECT_TIMEOUT = "连接超时,请稍后再试"
        const val BAD_NETWORK = "服务器异常"
        const val PARSE_ERROR = "解析服务器响应数据失败"
        const val UNKNOWN_ERROR = "未知错误"
        const val RESPONSE_RETURN_ERROR = "服务器返回数据失败"
    }

    override fun onComplete() {
        onRequestEnd()
    }

    override fun onSubscribe(d: Disposable) {
        onRequestStart()

    }

    override fun onNext(t: T) {
        onRequestEnd()
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        onRequestEnd()
        if (e is retrofit2.HttpException) { //HTTP错误

            onException(ExceptionReason.BAD_NETWORK)
        } else if (e is ConnectException || e is UnknownHostException) { //连接错误
            onException(ExceptionReason.CONNECT_ERROR)
        } else if (e is InterruptedIOException) { //连接超时
            onException(ExceptionReason.CONNECT_TIMEOUT)
        } else if (e is JsonParseException || e is JSONException || e is ParseException) { //解析错误
            onException(ExceptionReason.PARSE_ERROR)
        } else { //其他错误
            onException(ExceptionReason.UNKNOWN_ERROR)
        }
    }


    fun onException(reason: ExceptionReason) {
        when (reason) {
            ExceptionReason.CONNECT_ERROR -> ToastUtil.show(CONNECT_ERROR)
            ExceptionReason.CONNECT_TIMEOUT -> ToastUtil.show(CONNECT_TIMEOUT)
            ExceptionReason.BAD_NETWORK -> ToastUtil.show(BAD_NETWORK)
            ExceptionReason.PARSE_ERROR -> ToastUtil.show(PARSE_ERROR)
            ExceptionReason.UNKNOWN_ERROR -> ToastUtil.show(UNKNOWN_ERROR)
        }
    }

    /**
     * 网络请求成功并返回正确值
     *
     * @param response 返回值
     */
    abstract fun onSuccess(response: T)

    /**
     * 网络请求成功但是返回值是错误的
     *
     * @param response 返回值
     */
    open fun onFailing(message: String) {

        if (TextUtils.isEmpty(message)) {
            ToastUtil.show(RESPONSE_RETURN_ERROR)
        } else {
            ToastUtil.show(message)
        }
    }


    /**
     * 网络请求开始
     */
    fun onRequestStart() {
        if (mShowLoading) {
            showProgressDialog()
        }
    }

    /**
     * 网络请求结束
     */
    fun onRequestEnd() {
        closeProgressDialog()
    }

    private var projressDialog: Dialog? = null

    /**
     * 开启Dialog
     */
    fun showProgressDialog() {
        if (projressDialog == null) {
            val topActivity = ActivityStackManager.getTopActivity()
            if (topActivity != null) {
                projressDialog = Dialog(topActivity, R.style.dialog)
                projressDialog!!.setContentView(R.layout.dialog_progressbar)
            }
        }

        if (projressDialog != null && !projressDialog?.isShowing!!)
            projressDialog?.show()
    }

    /**
     * 关闭Dialog
     */
    fun closeProgressDialog() {
        if (projressDialog != null && projressDialog?.isShowing!!)
            projressDialog?.dismiss()
    }


    /**
     * 网络请求失败原因
     */
    enum class ExceptionReason {
        /**
         * 解析数据失败
         */
        PARSE_ERROR,
        /**
         * 网络问题
         */
        BAD_NETWORK,
        /**
         * 连接错误
         */
        CONNECT_ERROR,
        /**
         * 连接超时
         */
        CONNECT_TIMEOUT,
        /**
         * 未知错误
         */
        UNKNOWN_ERROR
    }
}
