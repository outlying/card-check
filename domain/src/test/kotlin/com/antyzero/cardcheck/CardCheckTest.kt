package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.Card
import org.amshove.kluent.`should start with`
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class CardCheckTest {

    @Test
    fun testInvalidCardType() {
        val cardCheck = CardCheck()

        val exception = assertThrows(IllegalArgumentException::class.java) {
            cardCheck.check(object : Card() {})
        }

        exception.message!! `should start with` "Unsupported card type"
    }
}