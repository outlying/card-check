package com.antyzero.cardcheck.version

import org.junit.Test
import org.threeten.bp.LocalDate
import rx.observers.TestSubscriber
import org.assertj.core.api.Assertions.*

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
            assertThat(it.first).isEqualTo(LocalDate.of(2014,8,22))
            assertThat(it.second).isEqualTo("7.0")
        }
    }

    @Test
    fun betaApplication() {

        // Given
        val applicationId = "com.antyzero.cardcheck"
        val checkLatestVersion = CheckLatestVersion()
        val testSubscriber = TestSubscriber<Pair<LocalDate, String?>>()

        // When
        checkLatestVersion.latestVersion(applicationId).subscribe(testSubscriber)

        // Then
        testSubscriber.assertNoErrors()
        testSubscriber.assertCompleted()
        testSubscriber.assertValueCount(1)

        testSubscriber.onNextEvents[0].let {
            assertThat(it.second).isEqualTo(null)
        }
    }
}