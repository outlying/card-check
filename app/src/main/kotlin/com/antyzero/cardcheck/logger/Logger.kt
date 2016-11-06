package com.antyzero.cardcheck.logger

interface Logger {

    fun v(tag: String, msg: String, throwable: Throwable)

    fun v(tag: String, msg: String)

    fun d(tag: String, msg: String, throwable: Throwable)

    fun d(tag: String, msg: String)

    fun i(tag: String, msg: String, throwable: Throwable)

    fun i(tag: String, msg: String)

    fun w(tag: String, msg: String, throwable: Throwable)

    fun w(tag: String, msg: String)

    fun e(tag: String, msg: String, throwable: Throwable)

    fun e(tag: String, msg: String)
}