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

        DialogPreference(context, attrs, defStyleAttr, defStyleRes), NumberPicker.OnValueChangeListener {

    private var selectedValue: Int = 0

    init {
        dialogLayoutResource = R.layout.preference_number_picker
        dialogIcon = null
        setPositiveButtonText(android.R.string.ok)
        setNegativeButtonText(android.R.string.cancel)
    }

    override fun onSetInitialValue(restorePersistedValue: Boolean, defaultValue: Any?) {
        if (restorePersistedValue) {
            selectedValue = getPersistedInt(Int.MIN_VALUE).apply {
                if (this == Int.MIN_VALUE) {
                    throw IllegalStateException("Failed to get persisted value")
                }
            }
        } else {
            selectedValue = defaultValue as Int
            persistInt(selectedValue)
        }
    }

    override fun showDialog(state: Bundle?) {
        super.showDialog(state)
        val numberPicker: NumberPicker = dialog.findViewById(R.id.numberPicker)

        numberPicker.minValue = 1
        numberPicker.maxValue = 20
        numberPicker.value = selectedValue
        numberPicker.setOnValueChangedListener(this)
    }

    override fun onDialogClosed(positiveResult: Boolean) {
        if (positiveResult) {
            persistInt(selectedValue)
        }
    }

    /**
     * Update on NumberPicker change
     */
    override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
        selectedValue = newVal
    }

    override fun onGetDefaultValue(typedArray: TypedArray, index: Int): Any {
        return typedArray.getInteger(index, 0)
    }
}