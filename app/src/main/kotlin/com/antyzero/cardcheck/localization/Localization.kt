package com.antyzero.cardcheck.localization

interface Localization {

    fun cardLastDays(days: Int): String

    fun validFor(days: Int): String

}