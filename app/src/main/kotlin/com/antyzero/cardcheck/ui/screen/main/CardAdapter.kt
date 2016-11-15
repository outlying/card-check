package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.dsl.extension.applicationComponent
import com.antyzero.cardcheck.dsl.extension.label
import com.antyzero.cardcheck.dsl.extension.setBackgroundColorRes
import com.antyzero.cardcheck.localization.Localization
import javax.inject.Inject


class CardAdapter(context: Context, val cards: List<Pair<Card, CardCheckResult>>) : RecyclerView.Adapter<CardViewHolder>() {

    @Inject lateinit var localization: Localization

    private val layoutInflater: LayoutInflater
    private var internalSelectableMode: Boolean = false

    init {
        context.applicationComponent().inject(this)
    }

    val selectedItems: MutableList<Pair<Card, CardCheckResult>> = mutableListOf()

    var selectableMode: Boolean
        get() = internalSelectableMode
        set(value) {
            selectableModeListener?.invoke(value)
            internalSelectableMode = value
            if (value == false) {
                clearSelected()
            }
        }

    var selectableModeListener: ((Boolean) -> Unit)? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false), this, localization)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    override fun getItemId(position: Int): Long {
        return cards[position].hashCode().toLong()
    }

    fun clearSelected() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}

class CardViewHolder(itemView: View, val cardAdapter: CardAdapter, private val localization: Localization) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener {

    private val textViewCardNameId: TextView
    private val textViewCardStatus: TextView
    private val cardIndicator: View
    private val context: Context = itemView.context

    private var internalSelected: Boolean = false

    var selected: Boolean
        get() = internalSelected
        set(value) {
            internalSelected = value

            if (value) {
                cardAdapter.selectedItems.add(cardAdapter.cards[adapterPosition])
                itemView.setBackgroundColorRes(R.color.colorAccent)
            } else {
                cardAdapter.selectedItems.remove(cardAdapter.cards[adapterPosition])
                itemView.setBackgroundColorRes(android.R.color.transparent)

                if (cardAdapter.selectedItems.isEmpty() && cardAdapter.selectableMode) {
                    cardAdapter.selectableMode = false
                }
            }
        }

    init {
        textViewCardNameId = itemView.findViewById(R.id.textViewCardNameId) as TextView
        textViewCardStatus = itemView.findViewById(R.id.textViewCardStatus) as TextView
        cardIndicator = itemView.findViewById(R.id.cardIndicator)

        itemView.setOnLongClickListener(this)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (cardAdapter.selectableMode) {
            selected = selected.not()
        }
    }

    override fun onLongClick(v: View): Boolean {
        if (cardAdapter.selectableMode == false) {
            cardAdapter.selectableMode = true
            selected = true
            return true
        }
        return false
    }

    fun bind(cardData: Pair<Card, CardCheckResult>) {

        selected = cardAdapter.selectedItems.contains(cardData)

        val (card, status) = cardData
        if (card is MpkCard) {
            textViewCardNameId.text = "${card.cardType.label(context)} \n#${card.clientId}"
        }

        when (status) {
            is CardCheckResult.UnableToGetInformation -> {
                textViewCardStatus.text = getString(R.string.card_status_unknown)
            }
            is CardCheckResult.Expired -> {
                textViewCardStatus.text = getString(R.string.card_status_expired)
            }
            is CardCheckResult.NotExpired -> {
                textViewCardStatus.text = "${getString(R.string.card_status_valid)}\n${localization.validFor(status.daysLeft)}"
            }
        }
    }

    private fun getString(@StringRes stringId: Int): String {
        return context.getString(stringId)
    }
}
