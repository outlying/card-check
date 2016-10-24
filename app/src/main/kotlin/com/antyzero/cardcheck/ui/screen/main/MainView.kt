package com.antyzero.cardcheck.ui.screen.main

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.mvp.View

interface MainView : View {

    fun showCards(it: List<Pair<Card, CardCheckResult>>)

    fun goToAddCardScreen()
}