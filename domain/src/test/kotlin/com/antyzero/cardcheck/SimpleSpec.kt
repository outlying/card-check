package com.antyzero.cardcheck

import org.amshove.kluent.`should be`
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it


class SimpleSpec : Spek({

    describe("a calculator") {

        val calculator = object : Any() {
            fun sum(x: Int, y: Int) = x + y
            fun subtract(x: Int, y: Int) = x-y
        }

        it("should return the result of adding the first number to the second number") {
            val sum = calculator.sum(2, 4)
            sum `should be` 6
        }

        it("should return the result of subtracting the second number from the first number") {
            val subtract = calculator.subtract(4, 2)
            subtract `should be` 2
        }
    }
})