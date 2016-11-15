package com.antyzero.cardcheck.localization

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class LocalizationTest {

    private lateinit var localization: Localization

    @Test
    fun testDaysLeftEn() {
        localization = LocalizationEn()
        assertThat(localization.cardLastDays(25)).isEqualToIgnoringCase("Your card will last 26 more days")
        assertThat(localization.cardLastDays(20)).isEqualToIgnoringCase("Your card will last 21 more days")
        assertThat(localization.cardLastDays(10)).isEqualToIgnoringCase("Your card will last 11 more days")
        assertThat(localization.cardLastDays(1)).isEqualToIgnoringCase("Your card will last until tomorrow")
        assertThat(localization.cardLastDays(0)).isEqualToIgnoringCase("Your card will last until today")
    }

    @Test
    fun testDaysLeftPl() {
        localization = LocalizationPl()
        assertThat(localization.cardLastDays(25)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 26 dni")
        assertThat(localization.cardLastDays(20)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 21 dni")
        assertThat(localization.cardLastDays(10)).isEqualToIgnoringCase("Karta będzie ważna jeszcze 11 dni")
        assertThat(localization.cardLastDays(1)).isEqualToIgnoringCase("Karta będzie ważna do jutra")
        assertThat(localization.cardLastDays(0)).isEqualToIgnoringCase("Karta będzie ważna do końca dnia")
    }

    @Test
    fun validForDaysEn() {
        localization = LocalizationEn()
        assertThat(localization.validFor(25)).isEqualToIgnoringCase("26 more days")
        assertThat(localization.validFor(20)).isEqualToIgnoringCase("21 more days")
        assertThat(localization.validFor(10)).isEqualToIgnoringCase("11 more days")
        assertThat(localization.validFor(1)).isEqualToIgnoringCase("till tomorrow")
        assertThat(localization.validFor(0)).isEqualToIgnoringCase("last day")
    }

    @Test
    fun validForDaysPl() {
        localization = LocalizationPl()
        assertThat(localization.validFor(25)).isEqualToIgnoringCase("26 dni")
        assertThat(localization.validFor(20)).isEqualToIgnoringCase("21 dni")
        assertThat(localization.validFor(10)).isEqualToIgnoringCase("11 dni")
        assertThat(localization.validFor(1)).isEqualToIgnoringCase("do jutra")
        assertThat(localization.validFor(0)).isEqualToIgnoringCase("ostatni dzień")
    }
}