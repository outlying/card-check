package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(
        MainModule::class
))
interface MainComponent {

    fun mainPresenter(): MainPresenter
}

@Module
class MainModule(val mainActivity: MainActivity) {

    @Provides
    fun provideMainPresenter(cardCheck: CardCheck, jobs: Jobs, logger: Logger) = MainPresenter(cardCheck, jobs, logger)
}

internal fun MainActivity.component() = applicationComponent().extend(MainModule(this))