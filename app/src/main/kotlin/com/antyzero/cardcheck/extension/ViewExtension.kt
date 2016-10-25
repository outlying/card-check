package com.antyzero.cardcheck.extension

import android.view.View

fun View.setVisible(boolean: Boolean) {
    this.visibility = if (boolean) {
        View.VISIBLE
    } else {
        View.GONE
    }
}