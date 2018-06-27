package com.antyzero.cardcheck.network

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request

class OkHttpRequester(private val okHttpClient: OkHttpClient) : Requester {

    override fun get(url: String): Single<String> {
        val request = Request.Builder().url(url).build()
        return Single.just(okHttpClient.newCall(request).execute().body()!!.string())
    }
}