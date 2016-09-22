package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import org.amshove.kluent.`should be`
import org.junit.Test
import org.threeten.bp.LocalDate

class CardVerificationTest {

    @Test
    fun testCheckValidCard() {

        // Given
        val checker = MpkChecker()

        // When
        val result = checker.check(MpkCard.Kkm(2170708, 20603546690), LocalDate.of(2016, 7, 28))

        // Then
        result `should be` CardCheckResult.NotExpired
    }

    @Test
    fun testNotValidCard() {

        // Given
        val checker = MpkChecker()

        // When
        val result = checker.check(MpkCard.Kkm(2130708, 20603146690), LocalDate.of(2016, 7, 28))

        // Then
        result `should be` CardCheckResult.Expired
    }
}