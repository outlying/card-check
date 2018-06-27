package com.antyzero.cardcheck.card

import io.reactivex.Single
import org.threeten.bp.LocalDate

interface Checker<in T : Card> {

    fun check(card: T): Single<CardCheckResult> = check(card, LocalDate.now())

    fun check(card: T, localDate: LocalDate): Single<CardCheckResult>
}