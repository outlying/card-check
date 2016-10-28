package com.antyzero.cardcheck.extension

import android.os.Build
import android.support.annotation.ColorRes
import android.view.View

fun View.setVisible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.setBackgroundColorRes(@ColorRes colorRes: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        this.setBackgroundColor(resources.getColor(colorRes, null))
    }else{
        this.setBackgroundColor(resources.getColor(colorRes))
    }
}