package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import io.reactivex.observers.TestObserver
import org.amshove.kluent.`should be`
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.LocalDate

class CardVerificationTest {

    private val card = MpkCard.Kkm(2170708, 20603546690)

    @Test
    fun validCard() {
        with(MpkChecker().check(card, LocalDate.of(2017, 7, 19)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            assertThat(result is CardCheckResult.Valid).isTrue().overridingErrorMessage("Got result $result, is not Valid")
            (result as CardCheckResult.Valid).daysLeft `should be` CardCheckResult.Valid(30).daysLeft
        }
    }

    @Test
    fun invalidCard() {
        with(MpkChecker().check(card, LocalDate.of(2000, 7, 28)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            (result is CardCheckResult.Expired) `should be` true
        }
    }

    // Test disabled
    fun accumulateRanges() {
        with(MpkChecker().check(card, LocalDate.of(2016, 10, 27)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            assert(result is CardCheckResult.Valid)
            (result as CardCheckResult.Valid).daysLeft `should be` CardCheckResult.Valid(61).daysLeft
        }
    }


}

val <T> TestObserver<T>.result: T
    get() = values()[0]