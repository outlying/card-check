package com.antyzero.cardcheck.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.preference.PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES
import com.antyzero.cardcheck.R

class ContextSettings(context: Context) : Settings {

    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    val keyDaysBeforeExpiration: String = context.getString(R.string.preference_days_before_expire)

    init {
        val readAgain = false // true for reset defaults
        val defaultValueSp = context.getSharedPreferences(
                PreferenceManager.KEY_HAS_SET_DEFAULT_VALUES, Context.MODE_PRIVATE)

        if (readAgain || !defaultValueSp.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {

            sharedPreferences.edit().apply {
                // Manually set default values
                putInt(keyDaysBeforeExpiration, Settings.DEFAULT_DAYS_BEFORE_CARD_EXPIRES)
            }.apply()

            val editor = defaultValueSp.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true)
            try {
                editor.apply()
            } catch (unused: AbstractMethodError) {
                editor.commit()
            }
        }
    }

    override var daysBeforeCardExpiration: Int
        get() = getInt(keyDaysBeforeExpiration)
        set(value) {
            sharedPreferences.edit()
                    .putInt(keyDaysBeforeExpiration, value).apply()
        }

    private fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, Int.MIN_VALUE).apply {
            if (this == Int.MIN_VALUE) {
                throw IllegalStateException("Default value for '$key' not defined")
            }
        }
    }
}