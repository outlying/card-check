package com.antyzero.cardcheck

import android.app.Application


class CardCheckApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .androidModule(AndroidModule(this))
                .build()
    }
}
