package com.antyzero.cardcheck.ui.inputfilter

import android.text.InputFilter
import android.text.Spanned
import java.math.BigDecimal

object OnlyPositiveLongInputFilter : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        if (dest.isNotEmpty()) {

            val bigDecimal: BigDecimal

            try {
                bigDecimal = BigDecimal(dest.toString())
            } catch (ignore: Exception) {
                return ""
            }

            if (bigDecimal < BigDecimal.ZERO) {
                return "0"
            }

            if (bigDecimal >= BigDecimal(Long.MAX_VALUE)) {
                return ""
            }
        }
        return null
    }
}