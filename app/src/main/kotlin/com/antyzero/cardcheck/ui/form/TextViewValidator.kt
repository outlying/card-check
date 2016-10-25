package com.antyzero.cardcheck.ui.form

import android.widget.TextView


class TextViewValidator(var textView: TextView) : ValidatorCollection() {

}

fun TextViewValidator.with(vararg validators: BaseTextViewValidator): TextViewValidator {
    validators.forEach {
        this.validators.add(it)
        it.textView = this.textView
    }
    return this
}