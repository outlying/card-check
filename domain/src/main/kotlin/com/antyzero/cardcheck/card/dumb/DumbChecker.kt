package com.antyzero.cardcheck.card.dumb

import com.antyzero.cardcheck.CardCheckResult
import com.antyzero.cardcheck.Checker
import org.threeten.bp.LocalDateTime

class DumbChecker(val result:CardCheckResult) : Checker<DumbCard> {

    override fun check(card: DumbCard, localDateTime: LocalDateTime): CardCheckResult {
        return result
    }
}