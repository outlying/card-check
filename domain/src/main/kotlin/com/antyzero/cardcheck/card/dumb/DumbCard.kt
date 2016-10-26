package com.antyzero.cardcheck.card.dumb

import com.antyzero.cardcheck.card.Card

class DumbCard : Card() {

    fun test() = System.out.print("Test")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other.javaClass != this.javaClass) return false
        return true
    }

    override fun hashCode(): Int {
        return 697907138
    }
}