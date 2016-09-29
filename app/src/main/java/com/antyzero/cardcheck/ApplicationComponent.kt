package com.antyzero.cardcheck

import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = arrayOf(AndroidModule::class))
interface ApplicationComponent {

}