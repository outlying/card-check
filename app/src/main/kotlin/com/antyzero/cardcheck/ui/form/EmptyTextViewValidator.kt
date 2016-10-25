package com.antyzero.cardcheck.ui.form

import android.text.TextUtils


class EmptyTextViewValidator(private val error: CharSequence = "Field should not be empty"): BaseTextViewValidator(), Validator {

    override fun isValid(): Boolean {
        return TextUtils.isEmpty(textView.text).apply {
            if(this){
                textView.error = this@EmptyTextViewValidator.error
            }
        }.not()
    }
}