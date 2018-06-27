package com.antyzero.cardcheck.scheduler

import androidx.work.*
import java.util.concurrent.TimeUnit


class AndroidScheduler : Scheduler {

    private val workManager = WorkManager.getInstance() ?: throw IllegalStateException("Work enqueue failed")

    override fun scheduleCardsCheck() {

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val cardCheckWork = PeriodicWorkRequestBuilder<CardCheckWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
                CARD_CHECKING_WORK,
                ExistingPeriodicWorkPolicy.REPLACE,
                cardCheckWork)
    }

    override fun removeAll() {
        workManager.cancelAllWork()
    }

    companion object {

        private const val CARD_CHECKING_WORK = "some text"
    }
}