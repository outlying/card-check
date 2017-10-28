package com.antyzero.cardcheck.settings

import android.content.Context

class ContextSettings(context: Context) : Settings {

    private val sharedPreferences = context.getSharedPreferences("ContextSettings", Context.MODE_PRIVATE)

    override var daysBeforeCardExpiration: Int
        get() = sharedPreferences.getInt(KEY_DAYS_BEFORE_EXPIRATION, 45)
        set(value) {
            sharedPreferences.edit()
                    .putInt(KEY_DAYS_BEFORE_EXPIRATION, value).apply()
        }

    private companion object {

        val KEY_DAYS_BEFORE_EXPIRATION = "KEY_DAYS_BEFORE_EXPIRATION"
    }
}