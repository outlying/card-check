package com.antyzero.cardcheck.logger

import android.content.Context
import com.antyzero.cardcheck.BuildConfig
import com.antyzero.cardcheck.tracker.TrackManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return if (BuildConfig.DEBUG) AndroidLogger() else CrashlyticsLogger()
    }

    @Provides
    @Singleton
    fun provideTracker(context: Context): TrackManager {
        return TrackManager(context)
    }
}