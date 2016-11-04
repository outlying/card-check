package com.antyzero.cardcheck.ui.notification

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class NotificationModule {

    @Provides
    @Singleton
    fun providesCardNotification(context: Context): CardNotification {
        return CardNotification(context)
    }
}