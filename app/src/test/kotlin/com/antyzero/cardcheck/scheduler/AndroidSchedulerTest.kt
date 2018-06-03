package com.antyzero.cardcheck.scheduler

import com.antyzero.cardcheck.BaseSchedulerTest


class AndroidSchedulerTest : BaseSchedulerTest() {

    override fun provideScheduler(): Scheduler = AndroidScheduler()
}