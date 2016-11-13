package com.antyzero.cardcheck.ui.notification

import android.content.Context
import com.antyzero.cardcheck.localization.Localization
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class NotificationModule {

    @Provides
    @Singleton
    fun providesCardNotification(context: Context, localization: Localization): CardNotification {
        return CardNotification(context, localization)
    }
}