package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.extension.applicationComponent
import com.antyzero.cardcheck.mvp.Presenter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class MainPresenter(context: Context) : Presenter<MainView> {

    @Inject lateinit var cardCheck: CardCheck
    lateinit var view: MainView

    init {
        context.applicationComponent().inject(this)
    }

    override fun attachView(view: MainView) {
        this.view = view
    }

    fun updateCardList() {
        Observable.from(cardCheck.getCards())
                .map {
                    Observable.zip(
                            Observable.just(it),
                            cardCheck.check(it),
                            { card: Card, cardCheckResult: CardCheckResult ->
                                card to cardCheckResult
                            }
                    )
                }
                .flatMap { it }
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showCards(it)
                })
    }
}
