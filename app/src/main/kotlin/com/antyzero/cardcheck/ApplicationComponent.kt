package com.antyzero.cardcheck

import com.antyzero.cardcheck.fabric.FabricModule
import com.antyzero.cardcheck.job.CardCheckJobService
import com.antyzero.cardcheck.job.JobModule
import com.antyzero.cardcheck.localization.LocalizationModule
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.logger.LoggerModule
import com.antyzero.cardcheck.network.NetworkModule
import com.antyzero.cardcheck.ui.notification.NotificationModule
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardPresenter
import com.antyzero.cardcheck.ui.screen.main.*
import com.antyzero.cardcheck.version.CheckLatestVersion
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(
        AndroidModule::class,
        FabricModule::class,
        NetworkModule::class,
        DomainModule::class,
        JobModule::class,
        NotificationModule::class,
        LoggerModule::class,
        LocalizationModule::class))

interface ApplicationComponent {

    fun inject(cardCheckApplication: CardCheckApplication)
    fun inject(cardCheckApplication: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(addCardPresenter: AddCardPresenter)
    fun inject(addCardActivity: AddCardActivity)
    fun inject(cardCheckJobService: CardCheckJobService)
    fun inject(cardAdapter: CardAdapter)

    // Graph

    fun extend(mainModule: MainModule): MainComponent

    // Exposed

    fun checkLatestVersion(): CheckLatestVersion
    fun logger(): Logger
}