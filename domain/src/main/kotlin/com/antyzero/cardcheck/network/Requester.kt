package com.antyzero.cardcheck.network

import io.reactivex.Flowable

interface Requester {

    fun get(url: String): Flowable<String>
}