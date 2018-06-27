package com.antyzero.cardcheck.ui.screen.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceFragment
import android.support.v7.app.AppCompatActivity
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.settings.ContextSettings
import javax.inject.Inject

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fragmentManager.beginTransaction().replace(android.R.id.content, SettingsFragment()).commit()
    }
}

class SettingsFragment : PreferenceFragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    @Inject
    lateinit var settings: ContextSettings

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preference_main)
        applicationComponent.inject(this)

        findPreference(settings.keyDaysBeforeCardExpiration).apply {
            setDefaultValue(settings.daysBeforeCardExpiration)
            summary = getString(R.string.days_before_expire_summary, settings.daysBeforeCardExpiration)
        }
    }

    override fun onResume() {
        super.onResume()
        settings.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        settings.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        findPreference(settings.keyDaysBeforeCardExpiration).apply {
            setDefaultValue(settings.daysBeforeCardExpiration)
            summary = getString(R.string.days_before_expire_summary, settings.daysBeforeCardExpiration)
        }
    }
}
