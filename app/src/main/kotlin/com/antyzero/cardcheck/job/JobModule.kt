package com.antyzero.cardcheck.job

import android.content.Context
import com.antyzero.cardcheck.logger.Logger
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
@Singleton
class JobModule {

    @Provides
    @Singleton
    fun provideJobDispatcher(context: Context): FirebaseJobDispatcher {
        return FirebaseJobDispatcher(GooglePlayDriver(context))
    }

    @Provides
    @Singleton
    fun provideJobs(firebaseJobDispatcher: FirebaseJobDispatcher, logger:Logger): Jobs {
        return Jobs(firebaseJobDispatcher, logger)
    }
}