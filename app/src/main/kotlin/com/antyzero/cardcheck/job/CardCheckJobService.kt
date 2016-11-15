package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.dsl.extension.tag
import com.antyzero.cardcheck.job.Jobs.Tags.CARD_CHECK
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.ui.notification.CardNotification
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class CardCheckJobService() : JobService() {

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var cardNotification: CardNotification
    @Inject lateinit var jobs: Jobs
    @Inject lateinit var logger: Logger

    override fun onCreate() {
        super.onCreate()
        applicationComponent().inject(this)
    }

    override fun onStartJob(job: JobParameters): Boolean {

        when (Jobs.Tags.findByName(job.tag)) {

            CARD_CHECK -> {
                Observable.from(cardCheck.getCards())
                        .compose(CardTransformer.status(cardCheck))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { cardNotification.cardStatus(it.first, it.second) },
                                { logger.w(tag(), "Cannot display notification", it) })

                // Re-schedule
                jobs.scheduleCardCheck()
            }
        }

        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }
}