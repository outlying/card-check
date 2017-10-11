package com.antyzero.cardcheck.localization

import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat

class LocalizationTest {

    private lateinit var localization: Localization

    @Test
    fun testDaysLeftEn() {
        localization = LocalizationEn()
        assertThat(localization.cardLastDays(25)).isEqualTo("Your card will last 26 more days")
        assertThat(localization.cardLastDays(20)).isEqualTo("Your card will last 21 more days")
        assertThat(localization.cardLastDays(10)).isEqualTo("Your card will last 11 more days")
        assertThat(localization.cardLastDays(1)).isEqualTo("Your card will last until tomorrow")
        assertThat(localization.cardLastDays(0)).isEqualTo("Your card will last until today")
    }

    @Test
    fun testDaysLeftPl() {
        localization = LocalizationPl()
        assertThat(localization.cardLastDays(25)).isEqualTo("Karta będzie ważna jeszcze 26 dni")
        assertThat(localization.cardLastDays(20)).isEqualTo("Karta będzie ważna jeszcze 21 dni")
        assertThat(localization.cardLastDays(10)).isEqualTo("Karta będzie ważna jeszcze 11 dni")
        assertThat(localization.cardLastDays(1)).isEqualTo("Karta będzie ważna do jutra")
        assertThat(localization.cardLastDays(0)).isEqualTo("Karta będzie ważna do końca dnia")
    }

    @Test
    fun validForDaysEn() {
        localization = LocalizationEn()
        assertThat(localization.validFor(25)).isEqualTo("26 more days")
        assertThat(localization.validFor(20)).isEqualTo("21 more days")
        assertThat(localization.validFor(10)).isEqualTo("11 more days")
        assertThat(localization.validFor(1)).isEqualTo("till tomorrow")
        assertThat(localization.validFor(0)).isEqualTo("last day")
    }

    @Test
    fun validForDaysPl() {
        localization = LocalizationPl()
        assertThat(localization.validFor(25)).isEqualTo("26 dni")
        assertThat(localization.validFor(20)).isEqualTo("21 dni")
        assertThat(localization.validFor(10)).isEqualTo("11 dni")
        assertThat(localization.validFor(1)).isEqualTo("do jutra")
        assertThat(localization.validFor(0)).isEqualTo("ostatni dzień")
    }
}