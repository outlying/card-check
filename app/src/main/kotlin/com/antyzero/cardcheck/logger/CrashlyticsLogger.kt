package com.antyzero.cardcheck.logger

import android.util.Log
import com.crashlytics.android.Crashlytics

class CrashlyticsLogger : Logger {

    override fun v(tag: String, msg: String, throwable: Throwable) {
        Crashlytics.log(Log.VERBOSE, tag, msg)
        Crashlytics.logException(throwable)
    }

    override fun v(tag: String, msg: String) {
        Crashlytics.log(Log.VERBOSE, tag, msg)
    }

    override fun d(tag: String, msg: String, throwable: Throwable) {
        Crashlytics.log(Log.DEBUG, tag, msg)
        Crashlytics.logException(throwable)
    }

    override fun d(tag: String, msg: String) {
        Crashlytics.log(Log.DEBUG, tag, msg)
    }

    override fun i(tag: String, msg: String, throwable: Throwable) {
        Crashlytics.log(Log.INFO, tag, msg)
        Crashlytics.logException(throwable)
    }

    override fun i(tag: String, msg: String) {
        Crashlytics.log(Log.INFO, tag, msg)
    }

    override fun w(tag: String, msg: String, throwable: Throwable) {
        Crashlytics.log(Log.WARN, tag, msg)
        Crashlytics.logException(throwable)
    }

    override fun w(tag: String, msg: String) {
        Crashlytics.log(Log.WARN, tag, msg)
    }

    override fun e(tag: String, msg: String, throwable: Throwable) {
        Crashlytics.log(Log.ERROR, tag, msg)
        Crashlytics.logException(throwable)
    }

    override fun e(tag: String, msg: String) {
        Crashlytics.log(Log.ERROR, tag, msg)
    }
}