package com.antyzero.cardcheck.data

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction

object CardTransformer {

    fun status(checker: Checker<Card>): ObservableTransformer<Card, Pair<Card, CardCheckResult>> {
        return ObservableTransformer {
            it.map {
                Observable.zip(
                        Observable.just(it),
                        checker.check(it).toObservable(),
                        BiFunction<Card, CardCheckResult, Pair<Card, CardCheckResult>> { card, cardCheckResult ->
                            card to cardCheckResult
                        }
                )
            }.flatMap { it }
        }
    }
}