package com.antyzero.cardcheck.ui.screen.main

import android.annotation.TargetApi
import android.content.Intent
import android.content.Intent.EXTRA_EMAIL
import android.os.Build.VERSION_CODES.LOLLIPOP
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.dsl.api
import com.antyzero.cardcheck.dsl.extension.browse
import com.antyzero.cardcheck.dsl.extension.browseWithChooser
import com.antyzero.cardcheck.dsl.extension.dip2pixels
import com.antyzero.cardcheck.dsl.extension.startActivity
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.dialog.RulesDialog
import com.antyzero.cardcheck.ui.screen.addcard.AddCardActivity
import com.crashlytics.android.Crashlytics
import com.crashlytics.android.answers.Answers
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainView, ActionMode.Callback {

    lateinit private var presenter: MainPresenter
    lateinit private var cardAdapter: CardAdapter
    private val cards: MutableList<Pair<Card, CardCheckResult>> = mutableListOf()
    private var actionMode: ActionMode? = null

    @TargetApi(LOLLIPOP) // TODO not cool but lint insist
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics(), Answers()) // TODO move to module

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter = MainPresenter(this).apply {
            this.attachView(this@MainActivity)
        }

        button.setOnClickListener { goToAddCardScreen() }
        button.api(LOLLIPOP) {
            setOnTouchListener { view: View, event: MotionEvent ->
                event.let {
                    when (it.action and MotionEvent.ACTION_MASK) {
                        MotionEvent.ACTION_DOWN -> button.elevation = dip2pixels(16)
                        MotionEvent.ACTION_UP -> button.elevation = dip2pixels(4)
                    }
                }
                false
            }
        }

        swipeRefresh.setOnRefreshListener { presenter.loadCardList() }

        cardAdapter = CardAdapter(this, cards)
        cardAdapter.selectableModeListener = { if (it) startActionMode() else actionMode?.finish() }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = cardAdapter

        RulesDialog.showIfRequired(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_contact -> {
                val emailIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "plain/text"
                    putExtra(EXTRA_EMAIL, arrayOf("cardcheck-android@googlegroups.com"))
                }

                if (emailIntent.resolveActivity(packageManager) != null) {
                    startActivity(Intent.createChooser(emailIntent, getString(R.string.email_send)))
                } else {
                    browseWithChooser("http://cardcheck.antyzero.com")
                }
            }
            R.id.action_edit -> cardAdapter.selectableMode = true
            R.id.action_forum -> browse("http://cardcheck.antyzero.com")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startActionMode() {
        actionMode = startActionMode(this)
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        button.visibility = GONE
        menuInflater.inflate(R.menu.main_action, menu);
        return true
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
        button.visibility = VISIBLE
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