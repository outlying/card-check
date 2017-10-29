package com.antyzero.cardcheck.dsl.extension

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.StringRes
import android.support.v4.app.NotificationManagerCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.widget.Toast
import com.antyzero.cardcheck.BuildConfig
import com.antyzero.cardcheck.CardCheckApplication
import kotlin.reflect.KClass

val Context.application
    get() = this.applicationContext as CardCheckApplication

val Context.cardCheck
    get() = application.cardCheck

fun Context.toast() = this.toast(this.toString())

fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes id: Int) = Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

fun Context.startActivity(activityClass: KClass<out Activity>) = this.startActivity(Intent(this, activityClass.java))

fun Context.browse(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Context.browseWithChooser(url: String) = startActivity(Intent.createChooser(Intent(Intent.ACTION_VIEW, Uri.parse(url)), "Open website"))

fun Context.layoutInflater() = LayoutInflater.from(this)

fun Context.notificationManager() = NotificationManagerCompat.from(this)

fun Context.pendingActivityIntent(requestCode: Int, activityClass: Class<out Activity>, flags: Int): PendingIntent {
    return PendingIntent.getActivity(this, requestCode, Intent(this, activityClass), flags)
}

fun Context.dip2pixels(dip: Number) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip.toFloat(), resources.displayMetrics)

fun Context.uninstall() {
    val packageUri = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
    val uninstallIntent = Intent(Intent.ACTION_UNINSTALL_PACKAGE, packageUri)
    startActivity(uninstallIntent)
}

fun Context.update() = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${BuildConfig.APPLICATION_ID}")).startActivity(this)