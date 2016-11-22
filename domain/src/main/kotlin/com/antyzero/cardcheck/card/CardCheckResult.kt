package com.antyzero.cardcheck.card

/**
 * We need result representation of our check
 */
sealed class CardCheckResult {

    class Valid(val daysLeft: Int) : CardCheckResult(){
        override fun toString(): String {
            return "${Valid::class.java}, days left: $daysLeft"
        }
    }

    class Expired() : CardCheckResult()

    class UnableToGetInformation() : CardCheckResult()
}