package com.antyzero.cardcheck

import android.app.Application
import com.antyzero.cardcheck.job.Jobs
import javax.inject.Inject


class CardCheckApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var jobs: Jobs

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
        applicationComponent.inject(this)

        jobs.scheduleCardCheck()
    }
}
