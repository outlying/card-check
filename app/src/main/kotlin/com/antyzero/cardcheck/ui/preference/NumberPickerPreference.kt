package com.antyzero.cardcheck.ui.preference

import android.content.Context
import android.content.res.TypedArray
import android.os.Bundle
import android.preference.DialogPreference
import android.util.AttributeSet
import android.widget.NumberPicker
import com.antyzero.cardcheck.R

class NumberPickerPreference @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0) :

        DialogPreference(context, attrs, defStyleAttr, defStyleRes) {

    init {
        dialogLayoutResource = R.layout.preference_number_picker
        dialogIcon = null
        setPositiveButtonText(android.R.string.ok)
        setNegativeButtonText(android.R.string.cancel)
    }

    override fun showDialog(state: Bundle?) {
        super.showDialog(state)
        val numberPicker: NumberPicker = dialog.findViewById(R.id.numberPicker)

        numberPicker.minValue = 0
        numberPicker.maxValue = 100
    }

    override fun onGetDefaultValue(typedArray: TypedArray, index: Int): Any {
        return typedArray.getInteger(index, 0)
    }
}