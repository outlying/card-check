package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.mvp.Presenter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainPresenter(val cardCheck: CardCheck, val jobs: Jobs, val logger: Logger) : Presenter<MainView> {

    lateinit var view: MainView

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
