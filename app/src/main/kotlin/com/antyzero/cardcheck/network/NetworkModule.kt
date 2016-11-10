package com.antyzero.cardcheck.network

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Singleton
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

}