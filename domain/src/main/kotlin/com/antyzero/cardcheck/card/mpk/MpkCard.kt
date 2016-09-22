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

    class Kkm(clientId: Int, cardId: Long) : MpkCard(clientId, cardId, cardType = Type.KKM)
    class Student(clientId: Int, cardType: Type) : MpkCard(clientId = clientId, cityCardId = null, cardType = cardType) {
        init {
            if (cardType == Type.KKM) {
                throw IllegalArgumentException("For KKM cards use MpkCard.Kkm class")
            }
        }
    }

}