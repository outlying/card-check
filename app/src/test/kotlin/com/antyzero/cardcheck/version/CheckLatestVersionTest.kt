package com.antyzero.cardcheck.version

import com.antyzero.cardcheck.dsl.extension.abs
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit
import rx.observers.TestSubscriber

class CheckLatestVersionTest {

    @Test
    fun oldApp() {

        // Given
        // https://play.google.com/store/apps/details?id=makatea.free.calculator.widget&hl=en
        val applicationId = "makatea.free.calculator.widget"
        val checkLatestVersion = CheckLatestVersion()
        val testSubscriber = TestSubscriber<Pair<LocalDate, String?>>()

        // When
        checkLatestVersion.latestVersion(applicationId).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(1)

        testSubscriber.onNextEvents[0].let {
            assertThat(it.first).isEqualTo(LocalDate.of(2014, 8, 22))
            assertThat(it.second).isEqualTo("7.0")
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