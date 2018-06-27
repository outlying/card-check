package com.antyzero.cardcheck.settings


interface Settings {

    var daysBeforeCardExpiration: Int

    companion object Default : Settings {

        const val DEFAULT_DAYS_BEFORE_CARD_EXPIRES: Int = 5

        @Suppress("UNUSED_PARAMETER")
        override var daysBeforeCardExpiration: Int
            get() = DEFAULT_DAYS_BEFORE_CARD_EXPIRES
            set(value) {
                // do nothing
            }
    }
}