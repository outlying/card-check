package com.antyzero.cardcheck.localization


class LocalizationEn : Localization {

    override fun validFor(days: Int): String {
        return when (days) {
            1 -> "till tomorrow"
            0 -> "last day"
            else -> "${days.inc()} more days"
        }
    }

    override fun cardLastDays(days: Int): String {
        return when (days) {
            1 -> "Card will last until tomorrow"
            0 -> "Card will last until today"
            else -> "Card will last ${days.inc()} more days"
        }
    }
}