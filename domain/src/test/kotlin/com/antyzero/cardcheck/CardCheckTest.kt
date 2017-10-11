package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.dumb.DumbChecker
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class CardCheckTest {

    @Test
    fun testInvalidCardType() {

        // Given
        val cardCheck = CardCheck()

        // When
        //cardCheck.check(object : Card() {})

        // Then
        // we assume exception
    }

    @Test
    fun testDumbCardCheck() {

        // Given
        val assumedResult = CardCheckResult.Expired()
        val checker = DumbChecker(assumedResult)
        val card = DumbCard()

        // When
        with(checker.check(card).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            values()[0] `should be` assumedResult
        }
    }
}