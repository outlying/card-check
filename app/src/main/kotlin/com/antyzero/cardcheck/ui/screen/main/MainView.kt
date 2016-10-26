package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.mvp.View

interface MainView : View {

    fun showLoading()

    fun hideLoading()

    fun showCards(cardsWithResults: List<Pair<Card, CardCheckResult>>)

    fun goToAddCardScreen()
}