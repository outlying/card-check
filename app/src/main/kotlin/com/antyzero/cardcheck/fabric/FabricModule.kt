package com.antyzero.cardcheck.fabric

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FabricModule(private val crashlytics: Crashlytics, private val answers: Answers) {

    @Provides
    @Singleton
    fun provideCrashlytics(): Crashlytics = crashlytics

    @Provides
    @Singleton
    fun provideAnswers(): Answers = answers
}