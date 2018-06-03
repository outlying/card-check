package com.antyzero.cardcheck.network

import com.antyzero.cardcheck.version.CheckLatestVersion
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    fun provideCheckLatestVersion(okHttpClient: OkHttpClient): CheckLatestVersion {
        return CheckLatestVersion(okHttpClient)
    }

}