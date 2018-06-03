package com.antyzero.cardcheck.dsl.extension

import android.app.Fragment
import android.content.Context


val Context.applicationComponent
    get() = this.application.applicationComponent

val Fragment.applicationComponent
    get() = this.activity?.application?.applicationComponent ?: throw IllegalStateException("Activity not available")

fun Context.checkLatestVersion() = this.applicationComponent.checkLatestVersion()