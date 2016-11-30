package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.mvp.Presenter
import com.antyzero.cardcheck.ui.notification.CardNotification
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class MainPresenter(context: Context) : Presenter<MainView> {

    @Inject lateinit var cardCheck: CardCheck
    @Inject lateinit var jobs: Jobs
    @Inject lateinit var logger: Logger
    @Inject lateinit var cardNotification: CardNotification
    lateinit var view: MainView

    init {
        context.applicationComponent().inject(this)
    }

    override fun attachView(view: MainView) {
        this.view = view
    }

    fun loadCardList() {
        Observable.from(cardCheck.getCards())
                .compose(CardTransformer.status(cardCheck))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                }
                .subscribe(
                        { view.showCards(it) },
                        { view.hideLoading() },
                        { view.hideLoading() })

        jobs.scheduleCardCheck()
    }

    fun removeCard(card: Card) {
        cardCheck.removeCard(card)
    }
}
