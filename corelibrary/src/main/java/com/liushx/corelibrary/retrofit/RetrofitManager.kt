package com.liushx.corelibrary.retrofit

import com.liushx.corelibrary.config.RetrofitConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitManager {
    private var mRetrofit: Retrofit? = null

    private object Config {
        val repository = RetrofitManager()
    }

    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderConfigInterceptor())
        .build()

    init {
        mRetrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(RetrofitConfig.baseUrl)
            .client(httpClient)
            .build()

    }

    companion object {
        /**
         * 获取请求
         */
        fun getInstance(): RetrofitManager {
            return Config.repository
        }
    }

    fun <T> create(cls: Class<T>): T {

        return mRetrofit!!.create(cls)
    }
}