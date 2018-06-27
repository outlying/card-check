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
sealed class MpkCard(val clientId: Long, val cardType: Type = Type.KKM) : Card() {

    class Kkm(clientId: Long, val cityCardId: Long) : MpkCard(clientId, cardType = Type.KKM) {

        override fun toString(): String {
            return "KKM #$cityCardId"
        }
    }

    class Student(clientId: Long, cardType: Type) : MpkCard(clientId = clientId, cardType = cardType) {

        init {
            if (cardType == Type.KKM) {
                throw IllegalArgumentException("For KKM cards use MpkCard.Kkm class")
            }
        }

        override fun toString(): String {
            return "$cardType #$clientId"
        }
    }

    // TODO as soon as Kotlin 1.1 is released we can change above classes to data classes
    // then solution below won't be needed anymore

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != this.javaClass) return false

        val kkm: Kkm = other as? Kkm ?: return false

        if (clientId != kkm.clientId) return false
        if (cardType != kkm.cardType) return false
        return true
    }

    override fun hashCode(): Int {
        var result = cardType.hashCode()
        result = 31 * result + clientId.hashCode()
        return result
    }

    /**
     * ...
     */
    enum class Type(val typeId: Long) {

        /* Default type */
        KKM(0),

        /* Types for student college cards */
        WZSIB(20),
        AGH(21), UJ(22), PK(23), UE(24), UR(25), PWST(26), AM(27), WSE(28), AIK(29), UP(30), WSH(31), KA(32), WSEI(33), IFJ_PAN(34), IF_PAN(35), IKIFP_PAN(36)
        ;

        override fun toString(): String {
            return name.replace("_", " ")
        }

        companion object {

            fun findByTypeId(typeId: Number): Type {
                val searchById = typeId.toLong()
                values().forEach {
                    if (it.typeId == searchById) {
                        return it
                    }
                }
                throw IllegalArgumentException("No enum value for type ID: $typeId")
            }
        }
    }
}