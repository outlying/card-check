package com.antyzero.cardcheck.card

import io.reactivex.Observable
import org.threeten.bp.LocalDate

interface Checker<in T : Card> {

    fun check(card: T): Observable<CardCheckResult> = check(card, LocalDate.now())

    fun check(card: T, localDate: LocalDate): Observable<CardCheckResult>
}