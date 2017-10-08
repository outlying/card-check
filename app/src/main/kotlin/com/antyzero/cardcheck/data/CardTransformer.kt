package com.antyzero.cardcheck.data

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker

object CardTransformer {

    fun status(checker: Checker<Card>): Observable.Transformer<Card, Pair<Card, CardCheckResult>> {

        return Observable.Transformer<Card, Pair<Card, CardCheckResult>> {
            it.map {
                Observable.zip(
                        Observable.just(it),
                        checker.check(it),
                        { card: Card, cardCheckResult: CardCheckResult ->
                            card to cardCheckResult
                        }
                )
            }.flatMap { it }
        }
    }
}