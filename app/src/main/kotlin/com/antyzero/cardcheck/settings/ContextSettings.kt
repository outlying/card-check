package com.antyzero.cardcheck.settings

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.antyzero.cardcheck.R

class ContextSettings(context: Context) : Settings {

    val sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    val keyDaysBeforeExpiration: String = context.getString(R.string.preference_days_before_expire)

    override var daysBeforeCardExpiration: Int
        get() = sharedPreferences.getInt(keyDaysBeforeExpiration, Settings.DEFAULT_DAYS_BEFORE_CARD_EXPIRES)
        set(value) {
            sharedPreferences.edit()
                    .putInt(keyDaysBeforeExpiration, value).apply()
        }
}