package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import org.threeten.bp.LocalDateTime

interface Checker<in T : Card> {

    fun check(card: T): CardCheckResult = check(card, LocalDateTime.now())

    fun check(card: T, localDateTime: LocalDateTime): CardCheckResult
}