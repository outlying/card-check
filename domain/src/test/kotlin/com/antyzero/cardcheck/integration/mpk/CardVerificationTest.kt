package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import org.amshove.kluent.`should be`
import org.junit.Test
import org.threeten.bp.LocalDate
import rx.observers.TestSubscriber

class CardVerificationTest {

    @Test
    fun testCheckValidCard() {

        // Given
        val checker = MpkChecker()
        val testSubscriber: TestSubscriber<CardCheckResult> = TestSubscriber()

        // When
        checker.check(MpkCard.Kkm(2170708, 20603546690), LocalDate.of(2016, 7, 19)).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        val cardCheckResult = testSubscriber.onNextEvents[0]
        assert(cardCheckResult is CardCheckResult.NotExpired)
        (cardCheckResult as CardCheckResult.NotExpired).daysLeft `should be` CardCheckResult.NotExpired(30).daysLeft
    }

    @Test
    fun testNotValidCard() {

        // Given
        val checker = MpkChecker()
        val testSubscriber: TestSubscriber<CardCheckResult> = TestSubscriber()

        // When
        checker.check(MpkCard.Kkm(2130708, 20603146690), LocalDate.of(2016, 7, 28)).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        assert(testSubscriber.onNextEvents[0] is CardCheckResult.Expired)
    }
}