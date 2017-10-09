package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import io.reactivex.observers.TestObserver
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.junit.Test
import org.threeten.bp.LocalDate

class CardVerificationTest {

    private val card = MpkCard.Kkm(2170708, 20603546690)
    private val checker = MpkChecker(debug = true)

    @Test
    fun validCard() {
        with(checker.check(card, LocalDate.of(2016, 7, 19)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Valid::class.java)
            (result as CardCheckResult.Valid).daysLeft `should be` CardCheckResult.Valid(30).daysLeft
        }
    }

    // @Test test disabled
    fun invalidCard() {
        with(checker.check(card, LocalDate.of(2000, 7, 28)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Expired::class.java)
        }
    }

    // Test disabled
    fun accumulateRanges() {
        with(checker.check(card, LocalDate.of(2016, 10, 27)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Valid::class.java)
            (result as CardCheckResult.Valid).daysLeft `should be` CardCheckResult.Valid(61).daysLeft
        }
    }


}

val <T> TestObserver<T>.result: T
    get() = values()[0]