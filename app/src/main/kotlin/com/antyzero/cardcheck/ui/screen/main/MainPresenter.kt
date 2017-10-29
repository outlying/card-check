package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.data.CardTransformer
import com.antyzero.cardcheck.job.Jobs
import com.antyzero.cardcheck.logger.Logger
import com.antyzero.cardcheck.mvp.Presenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainPresenter(val cardCheck: CardCheck, val jobs: Jobs, val logger: Logger) : Presenter<MainView> {

    lateinit var view: MainView

    override fun attachView(view: MainView) {
        this.view = view
    }

    fun loadCardList() {
        Observable.fromIterable(cardCheck.getCards())
                .compose(CardTransformer.status(cardCheck))
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    view.showLoading()
                }
                .doAfterTerminate {
                    view.hideLoading()
                }
                .subscribe(
                        { cards -> view.showCards(cards) },
                        { view.hideLoading() })


        jobs.scheduleCardCheck()
    }

    fun removeCard(card: Card) {
        cardCheck.removeCard(card)
    }
}
