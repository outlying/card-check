package com.antyzero.cardcheck.ui.inputfilter

import android.text.InputFilter
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils

object OnlyDigitsInputFilter : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {

        val stringBuilder = StringBuilder()

        (start until end)
                .filter { source[it].isDigit() }
                .forEach { stringBuilder.append(source[it]) }

        val string = stringBuilder.toString()

        return if (source is Spanned) {
            val spannableString = SpannableString(string)
            TextUtils.copySpansFrom(source, start, end, null, spannableString, 0)
            spannableString
        } else {
            string
        }
    }
}