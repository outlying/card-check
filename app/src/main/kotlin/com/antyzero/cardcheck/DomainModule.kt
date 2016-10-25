package com.antyzero.cardcheck

import android.content.Context
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.storage.FileStorage
import com.antyzero.cardcheck.storage.PersistentStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideCardCheck(persistentStorage: PersistentStorage): CardCheck {
        return CardCheck(persistentStorage).apply {
            if(BuildConfig.DEBUG){
                addCard(MpkCard.Kkm(2170708, 20603546690))
            }
        }
    }

    @Provides
    @Singleton
    fun provideFileStorage(context: Context): PersistentStorage {
        return FileStorage("", context.getFileStreamPath("storage").absoluteFile)
    }
}