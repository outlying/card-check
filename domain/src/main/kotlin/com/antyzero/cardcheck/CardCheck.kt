package com.antyzero.cardcheck

import com.antyzero.cardcheck.CardCheckResult.Expired
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.dumb.DumbChecker
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import org.threeten.bp.LocalDateTime


class CardCheck : Checker<Card> {

    init {
    }

    // TODO load save card data

    // TODO check card valid

    /**
     * Check if card is not expired.
     *
     * We pass _card_ for check, alternatively we may specify if card is valid for given point
     * in time, but by default we check it against current moment
     */


    override fun check(card: Card, localDateTime: LocalDateTime): CardCheckResult {

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

        return when(card){
            is MpkCard -> MpkChecker().check(card, localDateTime)
            is DumbCard -> DumbChecker(Expired).check(card, localDateTime)
            else -> throw IllegalArgumentException("Unsupported card type: $card")
        }
    }
}

/**
 * We need result representation of our check
 */
enum class CardCheckResult {

     NotExpired, Expired, UnableToGetInformation
}