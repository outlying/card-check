package com.antyzero.cardcheck.localization

import android.content.Context
import android.os.Build
import dagger.Module
import dagger.Provides
import java.util.*
import javax.inject.Singleton


@Singleton
@Module
class LocalizationModule {

    @Provides
    @Singleton
    fun provideLocalization(context: Context): Localization {
        return when (getLocale(context)) {
            Locale("pl", "PL") -> LocalizationPl()
            else -> LocalizationEn()
        }
    }

    private fun getLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {
            @Suppress("DEPRECATION")
            context.resources.configuration.locale
        }
    }
}