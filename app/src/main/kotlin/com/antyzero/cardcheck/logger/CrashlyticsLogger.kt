package com.antyzero.cardcheck.logger

import android.util.Log
import com.crashlytics.android.Crashlytics

class CrashlyticsLogger(crashlyticsCore: Crashlytics) : Logger {

    private val crashlytics = crashlyticsCore.core

    override fun v(tag: String, msg: String, throwable: Throwable) {
        crashlytics.log(Log.VERBOSE, tag, msg)
        crashlytics.logException(throwable)
    }

    override fun v(tag: String, msg: String) {
        crashlytics.log(Log.VERBOSE, tag, msg)
    }

    override fun d(tag: String, msg: String, throwable: Throwable) {
        crashlytics.log(Log.DEBUG, tag, msg)
        crashlytics.logException(throwable)
    }

    override fun d(tag: String, msg: String) {
        crashlytics.log(Log.DEBUG, tag, msg)
    }

    override fun i(tag: String, msg: String, throwable: Throwable) {
        crashlytics.log(Log.INFO, tag, msg)
        crashlytics.logException(throwable)
    }

    override fun i(tag: String, msg: String) {
        crashlytics.log(Log.INFO, tag, msg)
    }

    override fun w(tag: String, msg: String, throwable: Throwable) {
        crashlytics.log(Log.WARN, tag, msg)
        crashlytics.logException(throwable)
    }

    override fun w(tag: String, msg: String) {
        crashlytics.log(Log.WARN, tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable) {
        crashlytics.log(Log.ERROR, tag, msg)
        crashlytics.logException(throwable)
    }

    override fun e(tag: String, msg: String) {
        crashlytics.log(Log.ERROR, tag, msg)
    }
}