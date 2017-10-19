package com.antyzero.cardcheck

import android.app.Application
import android.test.ApplicationTestCase
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Test


class ApplicationTest : ApplicationTestCase<Application>(Application::class.java) {

    @Test
    fun testDumb() {
        assertThat(true).isTrue()
    }
}