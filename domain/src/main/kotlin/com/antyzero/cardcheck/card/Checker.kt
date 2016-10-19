package com.antyzero.cardcheck.card

import org.threeten.bp.LocalDate
import rx.Observable

interface Checker<in T : Card> {

    fun check(card: T): Observable<CardCheckResult> = check(card, LocalDate.now())

    fun check(card: T, localDate: LocalDate): Observable<CardCheckResult>
}