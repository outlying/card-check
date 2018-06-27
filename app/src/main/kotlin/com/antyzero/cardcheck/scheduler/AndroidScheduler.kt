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
                cardCheckWork.hashCode().toString(), // TODO replace with something better
                ExistingPeriodicWorkPolicy.REPLACE,
                cardCheckWork)
    }

    override fun removeAll() {
        workManager.cancelAllWork()
    }
}