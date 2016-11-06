package com.antyzero.cardcheck.ui.screen.settings

import android.os.Bundle
import android.preference.PreferenceActivity
import android.preference.PreferenceFragment
import android.support.annotation.StringRes
import com.antyzero.cardcheck.R

class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }
}

class SettingsFragment : PreferenceFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_main)
    }

    private fun findPreference(@StringRes key: Int) {
        findPreference(getString(key))
    }
}
