package com.antyzero.cardcheck

import com.antyzero.cardcheck.card.mpk.reduceIf
import com.antyzero.cardcheck.dsl.abs
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test


class CollectionFunTest {

    @Test
    fun simpleReductionIf() {

        // Given
        val data = mutableListOf(1, 2, 8, 9)
        val expectedResult = listOf(3, 17)

        // When
        data.reduceIf({ x, y -> (x - y).abs() == 1 }, { x, y -> x + y })

        // Then
        data.containsAll(expectedResult).and(expectedResult.containsAll(data)) `should be` true
    }
}