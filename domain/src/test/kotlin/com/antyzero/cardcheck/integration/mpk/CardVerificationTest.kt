package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import org.amshove.kluent.`should be`
import org.junit.Test
import org.threeten.bp.LocalDate
import rx.observers.TestSubscriber

class CardVerificationTest {

    private val card = MpkCard.Kkm(2170708, 20603546690)

    @Test
    fun validCard() {

        // Given
        val checker = MpkChecker()
        val testSubscriber: TestSubscriber<CardCheckResult> = TestSubscriber()

        // When
        checker.check(card, LocalDate.of(2016, 7, 19)).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        val cardCheckResult = testSubscriber.result()
        assert(cardCheckResult is CardCheckResult.NotExpired)
        (cardCheckResult as CardCheckResult.NotExpired).daysLeft `should be` CardCheckResult.NotExpired(30).daysLeft
    }

    @Test
    fun invalidCard() {

        // Given
        val checker = MpkChecker()
        val testSubscriber: TestSubscriber<CardCheckResult> = TestSubscriber()

        // When
        checker.check(card, LocalDate.of(2000, 7, 28)).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        (testSubscriber.result() is CardCheckResult.Expired) `should be` true
    }

    @Test
    fun accumulateRanges() {

        // Given
        val checker = MpkChecker()
        val testSubscriber: TestSubscriber<CardCheckResult> = TestSubscriber()

        // When
        checker.check(card, LocalDate.of(2016, 10, 27)).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        val cardCheckResult = testSubscriber.result()
        assert(cardCheckResult is CardCheckResult.NotExpired)
        (cardCheckResult as CardCheckResult.NotExpired).daysLeft `should be` CardCheckResult.NotExpired(31).daysLeft
    }

    fun TestSubscriber<CardCheckResult>.result() = this.onNextEvents[0]
}