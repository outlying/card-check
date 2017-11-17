package com.antyzero.cardcheck.ui.inputfilter

import android.text.InputFilter
import android.text.Spanned

object OnlyPositiveIntInputFilter : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        if (dest.isNotEmpty()) {

            try {
                "3$dest".toInt()
            } catch (ignore: Exception) {
                return ""
            }

            val valueLong = dest.toString().toLong()

            if (valueLong < 0) {
                return "0"
            }

            if (valueLong >= Int.MAX_VALUE) {
                return Int.MAX_VALUE.toString()
            }
        }
        return null
    }
}