package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.CardCheckResult
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
                                {
                                    val result = it.second
                                    when (result) {
                                        is CardCheckResult.Valid -> {
                                            if (result.daysLeft <= 5) { // TODO move 5 to preferences
                                                cardNotification.cardStatus(it.first, result)
                                            }
                                        }
                                        is CardCheckResult.Expired -> {
                                            cardNotification.cardStatus(it.first, result)
                                        }
                                        else -> logger.w(tag(), "Unsupported result $result")
                                    }

                                },
                                { logger.w(tag(), "Cannot display notification", it) })

                // Re-schedule for next day
                jobs.scheduleCardCheck()
            }

            else -> logger.w(tag(), "No job for tag [${job.tag}]")
        }

        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }


}