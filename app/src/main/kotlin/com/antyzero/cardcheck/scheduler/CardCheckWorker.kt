package com.antyzero.cardcheck.scheduler

import androidx.work.Worker
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.dsl.extension.TAG
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.settings.Settings
import com.antyzero.cardcheck.tracker.Tracker
import com.antyzero.cardcheck.ui.notification.CardNotification
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CardCheckWorker : Worker() {

    @Inject
    lateinit var cardCheck: CardCheck
    @Inject
    lateinit var cardNotification: CardNotification
    @Inject
    lateinit var tracker: Tracker
    @Inject
    lateinit var settings: Settings

    init {
        applicationContext.applicationComponent.inject(this)
    }

    override fun doWork(): Result {
        Logger.i(TAG, "Work started")

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

        return Result.SUCCESS
    }
}