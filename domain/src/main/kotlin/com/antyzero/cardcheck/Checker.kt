package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import org.threeten.bp.LocalDate

interface Checker<in T : Card> {

    fun check(card: T): CardCheckResult = check(card, LocalDate.now())

    fun check(card: T, localDate: LocalDate): CardCheckResult
}