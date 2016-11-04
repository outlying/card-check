package com.antyzero.cardcheck.extension

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.annotation.StringRes
import android.support.v4.app.NotificationManagerCompat
import android.view.LayoutInflater
import android.widget.Toast
import com.antyzero.cardcheck.CardCheckApplication
import kotlin.reflect.KClass

fun Context.application() = this.applicationContext as CardCheckApplication

fun Context.toast() = this.toast(this.toString())

fun Context.toast(message: String?) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toast(@StringRes id: Int) = Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

fun Context.cardCheck() = application().cardCheck

fun Context.startActivity(activityClass: KClass<out Activity>) = this.startActivity(Intent(this, activityClass.java))

fun Context.startUrl(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

fun Context.layoutInflater() = LayoutInflater.from(this)

fun Context.notificationManager() = NotificationManagerCompat.from(this)

fun Context.pendingActivityIntent(requestCode: Int, activityClass: Class<out Activity>, flags: Int): PendingIntent {
    return PendingIntent.getActivity(this, requestCode, Intent(this, activityClass), flags)
}