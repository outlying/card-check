package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.MpkChecker
import com.antyzero.cardcheck.storage.FileStorage
import com.antyzero.cardcheck.storage.PersistentStorage
import io.reactivex.Observable
import okhttp3.OkHttpClient
import org.threeten.bp.LocalDate


class CardCheck(val okHttpClient: OkHttpClient = OkHttpClient(), storage: PersistentStorage = FileStorage()) : Checker<Card>, PersistentStorage by storage {

    override fun check(card: Card, localDate: LocalDate): Observable<CardCheckResult> {

        return when (card) {
            is MpkCard -> MpkChecker(okHttpClient).check(card, localDate)
            else -> throw IllegalArgumentException("Unsupported card type: ${card.javaClass}")
        }
    }
}

