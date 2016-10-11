package com.antyzero.cardcheck

import com.antyzero.cardcheck.ui.BaseActivity
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class, DomainModule::class))
interface ApplicationComponent {

    fun inject(baseActivity: BaseActivity)

    fun inject(cardCheckApplication: CardCheckApplication)
}