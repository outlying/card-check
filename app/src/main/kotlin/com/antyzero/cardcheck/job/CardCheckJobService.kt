package com.antyzero.cardcheck.job

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.dsl.extension.TAG
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.job.Jobs.Tag.CARD_CHECK
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.settings.Settings
import com.antyzero.cardcheck.tracker.Tracker
import com.antyzero.cardcheck.ui.notification.CardNotification
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CardCheckJobService : JobService() {

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var cardNotification: CardNotification
    @Inject lateinit var jobs: Jobs
    @Inject lateinit var tracker: Tracker
    @Inject lateinit var settings: Settings

    override fun onCreate() {
        super.onCreate()
        applicationComponent.inject(this)
    }

    override fun onStartJob(job: JobParameters): Boolean {

        when (Jobs.Tag.findByName(job.tag)) {

            CARD_CHECK -> {

                Observable.fromIterable(cardCheck.getCards())
                        .compose(CardTransformer.status(cardCheck))
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                {
                                    val result = it.second
                                    when (result) {
                                        is CardCheckResult.Valid -> {
                                            if (result.daysLeft <= settings.daysBeforeCardExpiration) {
                                                cardNotification.cardStatus(it.first, result)
                                            }
                                        }
                                        is CardCheckResult.Expired -> {
                                            cardNotification.cardStatus(it.first, result)
                                        }
                                        else -> Logger.w(TAG, "Unsupported result $result")
                                    }

                                },
                                {
                                    Logger.w(TAG, "Cannot display notification", it)
                                    tracker.unableToShowNotification(it)
                                })

                // Re-schedule for next day
                jobs.scheduleCardCheck()
            }
        }
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }


}