package com.antyzero.cardcheck.settings

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SettingsModule {

    private lateinit var context: Context

    private val contextSettings: ContextSettings by lazy {
        ContextSettings(context)
    }

    @Provides
    @Singleton
    fun provideSettings(context: Context): Settings {
        this.context = context
        return contextSettings
    }

    @Provides
    @Singleton
    fun provideContextSettings(context: Context): ContextSettings {
        this.context = context
        return contextSettings
    }
}