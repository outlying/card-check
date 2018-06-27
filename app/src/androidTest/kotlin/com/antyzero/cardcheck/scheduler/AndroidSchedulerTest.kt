package com.antyzero.cardcheck.scheduler

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.WorkManager
import com.antyzero.cardcheck.BaseSchedulerTest
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AndroidSchedulerTest : BaseSchedulerTest() {

    init {
        val context = InstrumentationRegistry.getTargetContext()
        val configuration = Configuration.Builder().build()
        WorkManager.initialize(context, configuration)
    }

    override fun provideScheduler(): Scheduler = AndroidScheduler()
}