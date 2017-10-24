package com.antyzero.cardcheck.settings

import org.amshove.kluent.`should be greater or equal to`
import org.junit.jupiter.api.Test

class SettingsTest {

    @Test
    internal fun `read days expected`() {
        val settings: Settings = Settings.Default

        settings.daysBeforeCardExpiration `should be greater or equal to` 5
    }
}