package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.card.Card

/**
 * Each MPK card is identified by two numbers:
 *
 * - Card ID, which is unique for all cards in MPK database / system
 * - Client ID for user identification
 *
 * However it's different for students cards. Students cards are only identified by album number,
 * but each college have it's own sets of numbers, ergo, we can have 1234 album number in college
 * A, B, C to Z.
 */
sealed class MpkCard(val clientId: Int, val cityCardId: Long?, val cardType: Type = Type.KKM) : Card() {

    class Kkm(clientId: Int, cardId: Long) : MpkCard(clientId, cityCardId = cardId, cardType = Type.KKM)

    class Student(clientId: Int, cardType: Type) : MpkCard(clientId = clientId, cityCardId = null, cardType = cardType) {
        init {
            if (cardType == Type.KKM) {
                throw IllegalArgumentException("For KKM cards use MpkCard.Kkm class")
            }
        }
    }

    // TODO as soon as Kotlin 1.1 is released we can change above classes to data classes
    // then solution below won't be needed anymore

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != this.javaClass) return false

        val kkm: Kkm = other as Kkm

        if (clientId != kkm.clientId) return false
        if (cityCardId != kkm.cityCardId) return false
        if (cardType != kkm.cardType) return false
        return true
    }

    override fun hashCode(): Int {
        var result = cardType.hashCode()
        result = 31 * result + clientId.hashCode()
        if (cityCardId != null) {
            result = 31 * result + cityCardId.hashCode()
        }
        return result
    }
}