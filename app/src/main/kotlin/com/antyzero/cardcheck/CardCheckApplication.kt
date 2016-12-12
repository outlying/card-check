package com.antyzero.cardcheck

import android.app.Application
import com.antyzero.cardcheck.dsl.extension.logger
import com.antyzero.cardcheck.dsl.extension.tag
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.tracker.TrackManager
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import javax.inject.Inject


class CardCheckApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var jobs: Jobs
    @Inject lateinit var trackManager: TrackManager
    @Inject lateinit var logger : Logger

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics(), Answers())
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
        applicationComponent.inject(this)

        jobs.scheduleCardCheck()

        logger.i(tag(), "First run: ${trackManager.firstRun}")
    }
}
