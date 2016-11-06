package com.antyzero.cardcheck.logger

import com.antyzero.cardcheck.BuildConfig
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
}