package com.antyzero.cardcheck.settings

import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test

class SettingsTest {

    @Test
    internal fun `read days expected`() {
        val settings: Settings = Settings.Default

        settings.daysBeforeCardExpiration `should be` Settings.DEFAULT_DAYS_BEFORE_CARD_EXPIRES
    }
}