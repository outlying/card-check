package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.data.CardTransformer
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
    }

    fun removeCard(card: Card) {
        cardCheck.removeCard(card)
    }
}
