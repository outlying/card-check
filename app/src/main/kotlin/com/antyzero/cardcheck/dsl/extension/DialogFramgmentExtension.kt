package com.antyzero.cardcheck.dsl.extension

import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.antyzero.cardcheck.R

@Suppress("DEPRECATION")
fun AlertDialog.Builder.setToolbar(toolbarText: String): Toolbar {
    val toolbar = View.inflate(context, R.layout.dialog_toolbar, null) as Toolbar
    toolbar.setTitleTextColor(context.resources.getColor(android.R.color.primary_text_dark))
    toolbar.setBackgroundColor(context.resources.getColor(R.color.colorPrimary))
    toolbar.title = toolbarText
    this.setCustomTitle(toolbar)
    return toolbar
}

fun AlertDialog.Builder.setPositiveButton(pair: Pair<String, DialogInterface.OnClickListener>): AlertDialog.Builder {
    setPositiveButton(pair.first, pair.second)
    return this
}

fun AlertDialog.Builder.setNegativeButton(pair: Pair<String, DialogInterface.OnClickListener>): AlertDialog.Builder {
    setNegativeButton(pair.first, pair.second)
    return this
}

fun Fragment.layoutInflater(): LayoutInflater = activity?.layoutInflater ?: throw IllegalStateException("Missing activity")

fun DialogFragment.show(appCompatActivity: AppCompatActivity, tag: String) = this.show(appCompatActivity.supportFragmentManager, tag)