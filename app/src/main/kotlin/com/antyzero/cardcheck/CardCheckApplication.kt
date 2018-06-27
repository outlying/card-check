package com.antyzero.cardcheck

import android.app.Application
import com.antyzero.cardcheck.dsl.extension.TAG
import com.antyzero.cardcheck.fabric.FabricModule
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.AndroidLogger
import com.antyzero.cardcheck.logger.CrashlyticsLogger
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.tracker.TrackManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import javax.inject.Inject


class CardCheckApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject
    lateinit var cardCheck: CardCheck
    @Inject
    lateinit var jobs: Jobs
    @Inject
    lateinit var trackManager: TrackManager

    override fun onCreate() {
        super.onCreate()
        val crashlytics = Crashlytics()
        val answers = Answers()

        // Setup loggers
        Logger.add(if (BuildConfig.DEBUG) AndroidLogger() else CrashlyticsLogger(crashlytics))

        Fabric.with(this, crashlytics, answers)
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .fabricModule(FabricModule(crashlytics, answers))
                .build()
        applicationComponent.inject(this)

        jobs.scheduleCardCheck()
        Logger.i(TAG, "First run: ${trackManager.firstRun}")
    }
}
