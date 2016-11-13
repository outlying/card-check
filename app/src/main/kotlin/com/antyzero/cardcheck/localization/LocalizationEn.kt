package com.antyzero.cardcheck.localization


class LocalizationEn : Localization {

    override fun cardLastDays(days: Int): String {
        return when(days) {
            1 -> "Your card will last until tomorrow"
            0 -> "Your card will last until today"
            else -> "Your card will last $days more days"
        }
    }
}