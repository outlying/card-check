package com.antyzero.cardcheck.ui.screen.main

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.extension.startActivity
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView, ActionMode.Callback {

    lateinit private var presenter: MainPresenter
    lateinit private var cardAdapter: CardAdapter
    private val cards: MutableList<Pair<Card, CardCheckResult>> = mutableListOf()
    private var actionMode: ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter = MainPresenter(this).apply {
            this.attachView(this@MainActivity)
        }
        button.setOnClickListener { goToAddCardScreen() }
        swipeRefresh.setOnRefreshListener { presenter.loadCardList() }

        cardAdapter = CardAdapter(this, cards)
        cardAdapter.selectableModeListener = { if (it) startActionMode() else actionMode?.finish() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cardAdapter
    }

    private fun startActionMode() {
        actionMode = startActionMode(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_action, menu); return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                cardAdapter.selectedItems.forEach { presenter.removeCard(it.first) }
                presenter.loadCardList()
                actionMode?.finish()
            }
            else -> return false
        }
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
        cardAdapter.selectableMode = false
    }

    override fun getSupportActionBar(): ActionBar {
        return super.getSupportActionBar()!! // we do have
    }

    override fun showLoading() {
        swipeRefresh.isRefreshing = true
    }

    override fun hideLoading() {
        swipeRefresh.isRefreshing = false
    }

    override fun onStart() {
        super.onStart()
        presenter.loadCardList()
    }

    override fun showCards(cardsWithResults: List<Pair<Card, CardCheckResult>>) {
        cards.apply {
            clear()
            addAll(cardsWithResults)
        }
        cardAdapter.notifyDataSetChanged()
    }

    override fun goToAddCardScreen() {
        startActivity(AddCardActivity::class)
    }
}