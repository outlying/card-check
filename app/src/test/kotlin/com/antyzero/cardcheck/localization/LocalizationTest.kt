package com.antyzero.cardcheck.localization

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class LocalizationTest {

    @Test
    fun testDaysLeftEn() {

        // Given
        val localization: Localization = LocalizationEn()

        // When
        // Then
        assertThat(localization.cardLastDays(25)).isEqualToIgnoringCase("Your card will last 25 more days")
        assertThat(localization.cardLastDays(20)).isEqualToIgnoringCase("Your card will last 20 more days")
        assertThat(localization.cardLastDays(10)).isEqualToIgnoringCase("Your card will last 10 more days")
        assertThat(localization.cardLastDays(1)).isEqualToIgnoringCase("Your card will last until tomorrow")
        assertThat(localization.cardLastDays(0)).isEqualToIgnoringCase("Your card will last until today")
    }

    @Test
    fun testDaysLeftPl() {

        // Given
        val localization: Localization = LocalizationPl()

        // When
        // Then
        assertThat(localization.cardLastDays(25)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 25 dni")
        assertThat(localization.cardLastDays(20)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 20 dni")
        assertThat(localization.cardLastDays(10)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 10 dni")
        assertThat(localization.cardLastDays(1)).isEqualToIgnoringCase("Karta będzie ważna do jutra")
        assertThat(localization.cardLastDays(0)).isEqualToIgnoringCase("Karta będzie ważna do końca dnia")
    }
}