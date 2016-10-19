package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.dumb.DumbChecker
import org.amshove.kluent.`should be`
import org.junit.Test
import rx.observers.TestSubscriber

class CardCheckTest {

    @Test(expected = IllegalArgumentException::class)
    fun testInvalidCardType() {

        // Given
        val cardCheck = CardCheck()

        // When
        cardCheck.check(object : Card() {})

        // Then
        // we assume exception
    }

    @Test
    fun testDumbCardCheck() {

        // Given
        val assumedResult = CardCheckResult.Expired()
        val checker = DumbChecker(assumedResult)
        val card = DumbCard()
        val testSubscriber = TestSubscriber<CardCheckResult>()

        // When
        checker.check(card).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(1)
        testSubscriber.onNextEvents[0] `should be` assumedResult
    }
}