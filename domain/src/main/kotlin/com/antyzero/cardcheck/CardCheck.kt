package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.CardCheckResult.Expired
import com.antyzero.cardcheck.card.Checker
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.dumb.DumbChecker
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import com.antyzero.cardcheck.storage.FileStorage
import com.antyzero.cardcheck.storage.PersistentStorage
import org.threeten.bp.LocalDate
import rx.Observable


class CardCheck(storage: PersistentStorage = FileStorage()) : Checker<Card>, PersistentStorage by storage {

    override fun check(card: Card, localDate: LocalDate): Observable<CardCheckResult> {

        return when (card) {
            is MpkCard -> MpkChecker().check(card, localDate)
            is DumbCard -> DumbChecker(Expired()).check(card, localDate) // TODO remove in future
            else -> throw IllegalArgumentException("Unsupported card type: ${card.javaClass}")
        }
    }
}

