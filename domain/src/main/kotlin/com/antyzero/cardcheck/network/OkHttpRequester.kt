package com.antyzero.cardcheck.network

import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpRequester(private val okHttpClient: OkHttpClient) : Requester {

    override fun get(url: String): Flowable<String> {
        println(url)
        val request = Request.Builder().url(url).build()
        return Flowable.just(okHttpClient.newCall(request).execute().body()!!.string())
    }
}