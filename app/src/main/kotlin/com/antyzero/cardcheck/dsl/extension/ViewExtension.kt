package com.antyzero.cardcheck.dsl.extension

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
<<<<<<< HEAD
        @Suppress("DEPRECATION")
=======
>>>>>>> d926d652853d8fb07acde6ff70b50ebf384d68b6
        this.setBackgroundColor(resources.getColor(colorRes))
    }
}