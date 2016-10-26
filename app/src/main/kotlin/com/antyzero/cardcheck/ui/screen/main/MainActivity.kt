package com.antyzero.cardcheck.ui.screen.main

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.extension.startActivity
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView {

    lateinit private var presenter: MainPresenter
    private val cards: MutableList<Pair<Card, CardCheckResult>> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter = MainPresenter(this).apply {
            this.attachView(this@MainActivity)
        }
        button.setOnClickListener { goToAddCardScreen() }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CardAdapter(this, cards)
        swipeRefresh.setOnRefreshListener { presenter.updateCardList() }
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun onStart() {
        super.onStart()
        presenter.updateCardList()
    }

    override fun showCards(cardsWithResults: List<Pair<Card, CardCheckResult>>) {
        cards.apply {
            clear()
            addAll(cardsWithResults)
        }
        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun goToAddCardScreen() {
        startActivity(AddCardActivity::class)
    }
}