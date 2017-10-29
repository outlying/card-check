package com.antyzero.cardcheck.version

import com.antyzero.cardcheck.dsl.extension.abs
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

class CheckLatestVersionTest {

    @Test
    fun oldApp() {

        // Given
        // https://play.google.com/store/apps/details?id=makatea.free.calculator.widget&hl=en
        val applicationId = "makatea.free.calculator.widget"
        val checkLatestVersion = CheckLatestVersion()

        with(checkLatestVersion.latestVersion(applicationId).test()) {

            awaitTerminalEvent()
            assertNoErrors()
            assertComplete()
            assertValueCount(1)

            values()[0].let {
                assertThat(it.first).isEqualTo(LocalDate.of(2014, 8, 22))
                assertThat(it.second).isEqualTo("7.0")
            }
        }
    }

    @Test
    fun buildDate() {

        // Given
        val checkLatestVersion = CheckLatestVersion()

        // When
        val dateTime = checkLatestVersion.apkBuildDate()

        // Then
        val minutes = ChronoUnit.MINUTES.between(dateTime, LocalDateTime.now()).abs()
        assertThat(minutes <= 10)
    }
}