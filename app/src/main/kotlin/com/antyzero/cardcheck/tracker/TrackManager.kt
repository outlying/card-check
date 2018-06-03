package com.antyzero.cardcheck.tracker

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.antyzero.cardcheck.dsl.extension.TAG
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

class TrackManager(context: Context, tracker: Tracker) : Tracker by tracker {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(TAG, MODE_PRIVATE)

    val firstRun: LocalDateTime
        get() = LocalDateTime.ofInstant(Instant.ofEpochMilli(sharedPreferences.getLong(KEY_FIRST_RUN_TIME, 0)), ZoneId.systemDefault())

    init {

        // Tracking
        trackFirstRun()
    }

    private fun trackFirstRun() {
        if (sharedPreferences.contains(KEY_FIRST_RUN_TIME).not()) {
            sharedPreferences.edit().putLong(KEY_FIRST_RUN_TIME, System.currentTimeMillis()).apply()
        }
    }

    private companion object {

        private val KEY_FIRST_RUN_TIME = "firstRunTime"
    }
}