package com.antyzero.cardcheck.network

import io.reactivex.Single

interface Requester {

    fun get(url: String): Single<String>
}