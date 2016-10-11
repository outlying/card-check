package com.antyzero.cardcheck.card.dumb

import com.antyzero.cardcheck.CardCheckResult
import com.antyzero.cardcheck.Checker
import org.threeten.bp.LocalDate
import rx.Observable

class DumbChecker(val result: CardCheckResult) : Checker<DumbCard> {

    override fun check(card: DumbCard, localDate: LocalDate): Observable<CardCheckResult> {
        return Observable.just(result)
    }
}