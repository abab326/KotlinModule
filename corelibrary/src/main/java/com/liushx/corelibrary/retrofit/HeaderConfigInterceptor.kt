package com.liushx.corelibrary.retrofit

import com.liushx.corelibrary.config.RetrofitConfig
import com.liushx.corelibrary.utils.PreferencesUtil
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class HeaderConfigInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        /*********************** 适配多baseURL **********************************/
        val request = chain.request()
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues = request.headers(RetrofitConfig.headerKeyModule)
        if (headerValues.isNotEmpty()) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader(RetrofitConfig.headerKeyModule)

            //匹配获得新的BaseUrl
            val newBaseUrl = when (headerValues[0]) {
                "pwp" -> HttpUrl.parse(
                    PreferencesUtil.get(RetrofitConfig.baseUrl, "pwp")
                )
                "bi" -> HttpUrl.parse(
                    PreferencesUtil.get(RetrofitConfig.baseUrl, "pwp")
                )
                else -> HttpUrl.parse(
                    PreferencesUtil.get(RetrofitConfig.baseUrl, "pwp")
                )
            }
            //从request中获取原有的HttpUrl实例oldHttpUrl
            val oldHttpUrl = request.url()
            //重建新的HttpUrl，修改需要修改的url部分
            val newFullUrl = oldHttpUrl
                .newBuilder()
                .scheme(newBaseUrl!!.scheme())
                .host(newBaseUrl.host())
                .port(newBaseUrl.port())
                .build()

            //重建这个request，通过builder.url(newFullUrl).build()；
            //然后返回一个response至此结束修改
            return chain.proceed(builder.url(newFullUrl).build())
        } else {
            return chain.proceed(request)
        }
    }
}