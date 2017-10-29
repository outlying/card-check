package com.antyzero.cardcheck

import com.antyzero.cardcheck.dsl.extension.abs
import com.antyzero.cardcheck.dsl.extension.betweenWithMidnight
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalTime
import org.threeten.bp.temporal.ChronoUnit

class TimeTest {

    @Test
    fun diffTime() {

        // Given
        val localTime: LocalTime = LocalTime.of(18, 46, 33)
        val targetTime: LocalTime = LocalTime.of(4, 0)

        // When
        val seconds = ChronoUnit.SECONDS.betweenWithMidnight(localTime, targetTime).abs()

        // Then
        assertThat(seconds).isEqualTo(9 * 60 * 60 + 13 * 60 + 27)
    }

    @Test
    fun midnightDiff() {

        // Given
        val localTime: LocalTime = LocalTime.of(23, 59, 59)
        val targetTime: LocalTime = LocalTime.of(0, 0, 1)

        // When
        val seconds = ChronoUnit.SECONDS.betweenWithMidnight(localTime, targetTime).abs()

        // Then
        assertThat(seconds).isEqualTo(2)
    }
}