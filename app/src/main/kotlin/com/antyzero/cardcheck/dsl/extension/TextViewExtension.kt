package com.antyzero.cardcheck.dsl.extension

import android.support.annotation.StringRes
import android.text.TextUtils
import android.widget.TextView

fun TextView.isEmpty() = TextUtils.isEmpty(this.text)

fun TextView.setError(@StringRes errorMessage: Int) {
    this.error = context.getString(errorMessage)
}