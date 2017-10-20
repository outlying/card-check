package com.antyzero.cardcheck.ui.screen.addcard

import android.content.Context
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.mvp.Presenter
import javax.inject.Inject

class AddCardPresenter(context: Context) : Presenter<AddCardView> {

    @Inject lateinit var cardCheck: CardCheck
    private lateinit var view: AddCardView

    init {
        context.applicationComponent.inject(this)
    }

    override fun attachView(view: AddCardView) {
        this.view = view
    }

    fun addCard(card: Card) {
        cardCheck.addCard(card)
    }
}