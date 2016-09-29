package com.antyzero.cardcheck

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideCardCheck(): CardCheck {
        return CardCheck()
    }
}