package com.antyzero.cardcheck

import android.app.Application
import android.test.ApplicationTestCase
import junit.framework.Assert
import org.junit.Test


class ApplicationTest : ApplicationTestCase<Application>(Application::class.java) {

    @Test
    fun testDumb() {
        Assert.assertTrue(true)
    }
}