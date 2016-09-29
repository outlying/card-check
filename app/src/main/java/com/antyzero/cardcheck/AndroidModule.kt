package com.antyzero.cardcheck

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AndroidModule(private val cardCheckApplication: CardCheckApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return cardCheckApplication.applicationContext
    }
}