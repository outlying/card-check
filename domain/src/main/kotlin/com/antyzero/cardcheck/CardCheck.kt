package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import org.threeten.bp.LocalDateTime


class CardCheck {

    // TODO load save card data

    // TODO check card valid

    /**
     * Check if card is not expired.
     *
     * We pass _card_ for check, alternatively we may specify if card is valid for given point
     * in time, but by default we check it against current moment
     */
    fun check(card: Card, localDateTime: LocalDateTime = LocalDateTime.now()):CardCheckResult {

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

        return CardCheckResult.NotExpired()
    }
}

/**
 * We need result representation of our check
 */
sealed class CardCheckResult {

    // So card is valid
    class NotExpired : CardCheckResult()

    // Card is expired, we should renew it / charge / update ...
    class Expired : CardCheckResult()

    // We were unable to determine card state, due to some issues (not specified)
    class UnableToGetInformation : CardCheckResult()
}