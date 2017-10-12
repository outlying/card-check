package com.antyzero.cardcheck.localization

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class LocalizationTest {

    @TestFactory
    fun validFor(): Collection<DynamicContainer> {
        return VALID_FOR_TEST_DATA.map { (localization, series) ->
            return@map localization to series.map { (days, string) ->
                dynamicTest(string) {
                    assertThat(localization.validFor(days)).isEqualTo(string)
                }
            }
        }.map { (localization, dynamicTests) ->
            DynamicContainer.dynamicContainer(localization.javaClass.simpleName, dynamicTests)
        }
    }

    @TestFactory
    fun cardLast(): Collection<DynamicContainer> {
        return CARD_LAST_TEST_DATA.map { (localization, series) ->
            return@map localization to series.map { (days, string) ->
                dynamicTest(string) {
                    assertThat(localization.cardLastDays(days)).isEqualTo(string)
                }
            }
        }.map { (localization, dynamicTests) ->
            DynamicContainer.dynamicContainer(localization.javaClass.simpleName, dynamicTests)
        }
    }

    companion object {

        private val LOCALIZATION_PL: Localization = LocalizationPl()
        private val LOCALIZATION_EN: Localization = LocalizationEn()

        private val CARD_LAST_TEST_DATA = arrayOf(

                LOCALIZATION_PL to arrayOf(
                        25 to "Karta będzie ważna jeszcze 26 dni",
                        25 to "Karta będzie ważna jeszcze 26 dni",
                        20 to "Karta będzie ważna jeszcze 21 dni",
                        10 to "Karta będzie ważna jeszcze 11 dni",
                        1 to "Karta będzie ważna do jutra",
                        0 to "Karta będzie ważna do końca dnia"),

                LOCALIZATION_EN to arrayOf(
                        25 to "Card will last 26 more days",
                        20 to "Card will last 21 more days",
                        10 to "Card will last 11 more days",
                        1 to "Card will last until tomorrow",
                        0 to "Card will last until today"))

        private val VALID_FOR_TEST_DATA = arrayOf(

                LOCALIZATION_PL to arrayOf(
                        25 to "26 dni",
                        20 to "21 dni",
                        10 to "11 dni",
                        1 to "do jutra",
                        0 to "ostatni dzień"),

                LOCALIZATION_EN to arrayOf(
                        25 to "26 more days",
                        20 to "21 more days",
                        10 to "11 more days",
                        1 to "till tomorrow",
                        0 to "last day")
        )
    }
}