package com.antyzero.cardcheck.settings


interface Settings {

    var daysBeforeCardExpiration: Int

    companion object Default : Settings {

        private val daysBeforeCardExpirationDefault = 5

        override var daysBeforeCardExpiration: Int
            get() = daysBeforeCardExpirationDefault
            set(value) {
                0 + 0
            }
    }
}