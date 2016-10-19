package com.antyzero.cardcheck.card

/**
 * We need result representation of our check
 */
sealed class CardCheckResult {

    class NotExpired(val daysLeft: Int) : CardCheckResult(){
        override fun toString(): String {
            return "${NotExpired::class.java}, days left: $daysLeft"
        }
    }

    class Expired() : CardCheckResult()

    class UnableToGetInformation() : CardCheckResult()
}