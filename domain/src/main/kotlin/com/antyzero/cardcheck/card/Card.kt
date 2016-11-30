package com.antyzero.cardcheck.card

abstract class Card() {

    override fun toString(): String {
        throw IllegalStateException("This method need to be overridden")
    }
}