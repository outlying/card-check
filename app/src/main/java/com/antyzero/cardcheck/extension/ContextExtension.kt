package com.antyzero.cardcheck.extension

import android.content.Context
import android.support.annotation.StringRes
import android.widget.Toast
import com.antyzero.cardcheck.CardCheckApplication

fun Context.application() = this.applicationContext as CardCheckApplication

fun Context.toast() = this.toast(this.toString())

fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes id: Int) = Toast.makeText(this, id, Toast.LENGTH_SHORT).show()