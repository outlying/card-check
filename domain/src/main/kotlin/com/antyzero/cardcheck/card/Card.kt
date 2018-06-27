package com.antyzero.cardcheck.card

/**
 * Base card class for domain cards
 */
abstract class Card {

    override fun toString(): String {
        throw IllegalStateException("This method need to be overridden")
    }
}