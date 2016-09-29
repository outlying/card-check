package com.antyzero.cardcheck

import com.antyzero.cardcheck.CardCheckResult.Expired
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.dumb.DumbChecker
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import com.antyzero.cardcheck.storage.FileStorage
import com.antyzero.cardcheck.storage.Storage
import org.threeten.bp.LocalDate
import rx.Observable


class CardCheck(storage: Storage = FileStorage()) : Checker<Card>, Storage by storage {

    override fun check(card: Card, localDate: LocalDate): Observable<CardCheckResult> {

        /*

        Flow ?

        1. Can we check this card in the first place ? Is it supported ?
        2. What components are associated with this card ?
        3. How can we check if card is valid ?

        Let's assume we have our expire check mechanism in place, we should expect to get same
        result type as in this function. So, our mechanism should tell if card is:

        * expired
        * not expired
        * we don't know

        We may say that our checking mechanism should fallow the same contract as this class

         */

        return when (card) {
            is MpkCard -> MpkChecker().check(card, localDate)
            is DumbCard -> DumbChecker(Expired).check(card, localDate) // TODO remove in future
            else -> throw IllegalArgumentException("Unsupported card type: ${card.javaClass}")
        }
    }
}

/**
 * We need result representation of our check
 */
enum class CardCheckResult {
    NotExpired, Expired, UnableToGetInformation
}