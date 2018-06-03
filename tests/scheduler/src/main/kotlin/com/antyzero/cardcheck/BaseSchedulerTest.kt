package com.antyzero.cardcheck

import com.antyzero.cardcheck.scheduler.Scheduler
import org.junit.jupiter.api.Test


abstract class BaseSchedulerTest {

    abstract fun provideScheduler(): Scheduler

    @Test
    fun addJobSchedule() {
        
    }

    @Test
    fun clearAllSchedules() {

    }
}