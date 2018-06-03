package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.job.Jobs
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
interface MainComponent {

    fun mainPresenter(): MainPresenter
}

@Module
class MainModule(val mainActivity: MainActivity) {

    @Provides
    fun provideMainPresenter(cardCheck: CardCheck, jobs: Jobs) = MainPresenter(cardCheck, jobs)
}

val MainActivity.component: MainComponent
    get() = applicationComponent.extend(MainModule(this))