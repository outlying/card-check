package com.antyzero.cardcheck.dsl.extension

import android.content.Context


fun Context.applicationComponent() = this.application().applicationComponent

fun Context.logger() = this.applicationComponent().logger()

fun Context.checkLatestVersion() = this.applicationComponent().checkLatestVersion()