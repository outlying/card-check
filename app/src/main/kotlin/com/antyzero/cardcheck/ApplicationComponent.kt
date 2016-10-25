package com.antyzero.cardcheck

import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardPresenter
import com.antyzero.cardcheck.ui.screen.main.MainActivity
import com.antyzero.cardcheck.ui.screen.main.MainPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class, DomainModule::class))
interface ApplicationComponent {

    fun inject(cardCheckApplication: CardCheckApplication)
    fun inject(cardCheckApplication: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(addCardPresenter: AddCardPresenter)
    fun inject(addCardActivity: AddCardActivity)
}