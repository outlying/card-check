package com.antyzero.cardcheck.ui.screen.main

import android.os.Bundle
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.extension.startActivity
import com.antyzero.cardcheck.extension.toast
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity

// val card = MpkCard.Kkm(2170708, 20603546690)
class MainActivity : BaseActivity(), MainView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val presenter = MainPresenter(this).apply {
            this.attachView(this@MainActivity)
        }
    }

    override fun showCards(it: List<Pair<Card, CardCheckResult>>) {
        // TODO card logic
        toast(it.size.toString())
    }

    override fun goToAddCardScreen() {
        startActivity(AddCardActivity::class)
    }
}