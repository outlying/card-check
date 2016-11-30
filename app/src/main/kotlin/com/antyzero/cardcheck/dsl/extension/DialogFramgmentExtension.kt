package com.antyzero.cardcheck.dsl.extension

import android.content.DialogInterface
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import com.antyzero.cardcheck.R

fun AlertDialog.Builder.setToolbar(toolbarText: String): Toolbar {
    val toolbar = LayoutInflater.from(context).inflate(R.layout.dialog_toolbar, null) as Toolbar
    toolbar.setTitleTextColor(context.resources.getColor(android.R.color.primary_text_dark,null))
    toolbar.setBackgroundColor(context.resources.getColor(R.color.colorPrimary,null))
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

fun Fragment.layoutInflater() = activity.layoutInflater

fun DialogFragment.show(appCompatActivity: AppCompatActivity, tag: String) = this.show(appCompatActivity.supportFragmentManager, tag)