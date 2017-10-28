package com.antyzero.cardcheck.integration.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import com.antyzero.cardcheck.card.mpk.MpkSites
import io.reactivex.Flowable
import io.reactivex.observers.TestObserver
import org.amshove.kluent.`should be instance of`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal to`
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate
import java.util.*

class CardVerificationTest {

    private val mpkSite = TestMpkSites()
    private val card = MpkCard.Kkm(2170708, 20603546690)
    private val checker = MpkChecker(mpkSite, debug = false)

    @Test
    fun validCard() {
        mpkSite.response("/mpk_valid.html")
        with(checker.check(card, LocalDate.of(2016, 7, 19)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Valid::class.java)
            (result as CardCheckResult.Valid).daysLeft `should be` CardCheckResult.Valid(30).daysLeft
        }
    }

    @Test
    fun invalidCard() {
        mpkSite.response("/mpk_invalid.html")
        with(checker.check(card, LocalDate.of(2000, 7, 28)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Expired::class.java)
        }
    }

    @Test
    fun accumulateRanges() {
        mpkSite.response("/mpk_accumulate.html")
        with(checker.check(card, LocalDate.of(2016, 10, 27)).test()) {
            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            result.`should be instance of`(CardCheckResult.Valid::class.java)
            (result as CardCheckResult.Valid).daysLeft `should equal to` 212
        }
    }

    private class TestMpkSites : MpkSites {

        val queue: Queue<String> = ArrayDeque<String>()

        fun response(responseFile: String) {
            val response = (javaClass::getResource)(responseFile).readText()
            queue.add(response)
        }

        override fun cardStatus(card: MpkCard, localDate: LocalDate): Flowable<String>
                = Flowable.just(queue.poll())

    }
}

val <T> TestObserver<T>.result: T
    get() = values()[0]