package com.antyzero.cardcheck

import com.antyzero.cardcheck.scheduler.Scheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test


abstract class BaseSchedulerTest {

    abstract fun provideScheduler(): Scheduler

    @Test
    @Disabled // TODO not yet ready
    fun addJobSchedule() {
        val schedule = provideScheduler()
        assertThat(schedule.addSchedule()).isTrue()
    }

    @Test
    fun clearAllSchedules() {

    }
}