package com.liushx.corelibrary.retrofit


import com.liushx.corelibrary.base.BaseApplication
import com.liushx.corelibrary.manager.NetWorkMonitorManager
import com.liushx.corelibrary.utils.NetStateUtils
import okhttp3.*
import retrofit2.HttpException

/**
 * 数据请求缓存设置
 */
class HttpCacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        //没网强制从缓存读取
        if (NetStateUtils.getAPNType(BaseApplication.getContext()) == 0) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }

        val originalResponse = chain.proceed(request)


        if (NetStateUtils.getAPNType(BaseApplication.getContext()) != 0) {
            //有网的时候读接口上的@Headers里的配置
            val cacheControl = request.cacheControl().toString()
            return originalResponse.newBuilder()
                .header("Cache-Control", cacheControl)
                .removeHeader("Pragma")
                .build()
        } else {

            val response = retrofit2.Response.error<String>(
                550,
                ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), "")
            )
            throw HttpException(response)

        }
    }

}