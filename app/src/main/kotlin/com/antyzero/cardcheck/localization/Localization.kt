package com.antyzero.cardcheck.localization

import com.antyzero.cardcheck.card.Card

interface Localization {

    fun cardLastDays(days: Int): String

    fun validFor(days: Int): String

}