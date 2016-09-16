package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.CardCheckResult
import com.antyzero.cardcheck.Checker
import org.threeten.bp.LocalDateTime

class MpkChecker : Checker<MpkCard>{

    override fun check(card: MpkCard, localDateTime: LocalDateTime): CardCheckResult {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}