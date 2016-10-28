package com.antyzero.cardcheck.extension

import android.support.annotation.ColorRes
import android.view.View

fun View.setVisible(boolean: Boolean) {
    this.visibility = if (boolean) View.VISIBLE else View.GONE
}

fun View.setBackgroundColorRes(@ColorRes colorRes: Int) = this.setBackgroundColor(resources.getColor(colorRes, null))