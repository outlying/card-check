package com.antyzero.cardcheck

import android.app.Application
import com.antyzero.cardcheck.dsl.extension.logger
import com.antyzero.cardcheck.dsl.extension.tag
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.tracker.Tracker
import javax.inject.Inject


class CardCheckApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var jobs: Jobs
    @Inject lateinit var tracker: Tracker
    @Inject lateinit var logger : Logger

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
        applicationComponent.inject(this)

        jobs.scheduleCardCheck()

        logger.i(tag(), "First run: ${tracker.firstRun}")
    }
}
