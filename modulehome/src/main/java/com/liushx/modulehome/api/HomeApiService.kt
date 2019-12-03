package com.liushx.modulehome.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

interface HomeApiService {
    @GET("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1572862922185&di=b7cea6da232b1e015e465a0cd3c3b036&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4610b912c8fcc3cef70d70409845d688d53f20f7.jpg")
    fun getImage(): Observable<ResponseBody>
}