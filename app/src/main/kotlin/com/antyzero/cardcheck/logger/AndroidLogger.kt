package com.antyzero.cardcheck.logger

import android.util.Log

class AndroidLogger : Logger {

    override fun v(tag: String, msg: String, throwable: Throwable) {
        Log.v(tag, msg, throwable)
    }

    override fun v(tag: String, msg: String) {
        Log.v(tag, msg)
    }

    override fun d(tag: String, msg: String, throwable: Throwable) {
        Log.d(tag, msg, throwable)
    }

    override fun d(tag: String, msg: String) {
        Log.d(tag, msg)
    }

    override fun i(tag: String, msg: String, throwable: Throwable) {
        Log.i(tag, msg, throwable)
    }

    override fun i(tag: String, msg: String) {
        Log.i(tag, msg)
    }

    override fun w(tag: String, msg: String, throwable: Throwable) {
        Log.w(tag, msg, throwable)
    }

    override fun w(tag: String, msg: String) {
        Log.w(tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable) {
        Log.e(tag, msg, throwable)
    }

    override fun e(tag: String, msg: String) {
        Log.e(tag, msg)
    }
}