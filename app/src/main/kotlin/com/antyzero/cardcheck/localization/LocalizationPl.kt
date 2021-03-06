package com.antyzero.cardcheck.localization


class LocalizationPl : Localization {

    override fun validFor(days: Int): String {
        return when (days) {
            1 -> "do jutra"
            0 -> "ostatni dzień"
            else -> "${days.inc()} dni"
        }
    }

    override fun cardLastDays(days: Int): String {
        return when (days) {
            1 -> "Karta będzie ważna do jutra"
            0 -> "Karta będzie ważna do końca dnia"
            else -> "Karta będzie ważna jeszcze ${days.inc()} dni"
        }
    }


}