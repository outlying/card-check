package com.antyzero.cardcheck.logger

import android.content.Context
import com.antyzero.cardcheck.BuildConfig
import com.antyzero.cardcheck.tracker.AggregationTracker
import com.antyzero.cardcheck.tracker.TrackManager
import com.antyzero.cardcheck.tracker.Tracker
import com.antyzero.cardcheck.tracker.answers.AnswersTracker
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(crashlytics: Crashlytics): Logger {
        return if (BuildConfig.DEBUG) AndroidLogger() else CrashlyticsLogger(crashlytics)
    }

    @Provides
    @Singleton
    fun provideTrackerManager(context: Context, answers: Answers): TrackManager {
        return TrackManager(context, AggregationTracker(
                AnswersTracker(answers)
        ))
    }

    @Provides
    @Singleton
    fun provideTracker(trackManager: TrackManager): Tracker = trackManager
}