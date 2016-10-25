package com.antyzero.cardcheck.ui.screen.main

import android.os.Bundle
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.extension.startActivity
import com.antyzero.cardcheck.extension.toast
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    lateinit private var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this).apply {
            this.attachView(this@MainActivity)
        }
        button.setOnClickListener { goToAddCardScreen() }
    }

    override fun onStart() {
        super.onStart()
        presenter.updateCardList()
    }

    override fun showCards(it: List<Pair<Card, CardCheckResult>>) {
        // TODO card logic
        it.first().let {
            toast("${it.second}")
        }
    }

    override fun goToAddCardScreen() {
        startActivity(AddCardActivity::class)
    }
}