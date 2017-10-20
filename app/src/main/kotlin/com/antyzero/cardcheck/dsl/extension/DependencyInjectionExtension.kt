package com.antyzero.cardcheck.dsl.extension

import android.content.Context


val Context.applicationComponent
    get() = this.application.applicationComponent

val Context.logger
    get() = this.applicationComponent.logger()

fun Context.checkLatestVersion() = this.applicationComponent.checkLatestVersion()