package com.antyzero.cardcheck

import com.antyzero.cardcheck.ui.MainActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class, DomainModule::class))
interface ApplicationComponent {

    fun inject(cardCheckApplication: CardCheckApplication)
    fun inject(cardCheckApplication: MainActivity)
}