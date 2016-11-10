package com.antyzero.cardcheck.dsl

import android.annotation.SuppressLint
import android.os.Build

@SuppressLint
inline fun <T, R> T.api(apiLevel: Int, block: T.() -> R) {
    if (Build.VERSION.SDK_INT >= apiLevel) {
        block()
    }
}