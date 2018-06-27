package com.antyzero.cardcheck

import com.antyzero.cardcheck.scheduler.Scheduler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test


abstract class BaseSchedulerTest {

    abstract fun provideScheduler(): Scheduler

    @Test
    fun addJobSchedule() {
        val schedule = provideScheduler()
        assertThat(schedule.scheduleCardsCheck()).isTrue()
    }

    @Test
    fun clearAllSchedules() {

    }
}