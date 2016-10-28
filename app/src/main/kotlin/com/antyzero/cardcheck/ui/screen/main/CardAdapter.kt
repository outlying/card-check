package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.extension.label
import com.antyzero.cardcheck.extension.setBackgroundColorRes
import com.antyzero.cardcheck.extension.toast


class CardAdapter(context: Context, val cards: List<Pair<Card, CardCheckResult>>) : RecyclerView.Adapter<CardViewHolder>() {

    private val layoutInflater: LayoutInflater
    private var internalSelectableMode: Boolean = false

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
        holder.bind(cards[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false), this)
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

class CardViewHolder(itemView: View, val cardAdapter: CardAdapter) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener, View.OnClickListener {

    private val textViewCardNameId: TextView
    private val textViewCardStatus: TextView
    private val cardIndicator: View

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

                if(cardAdapter.selectedItems.isEmpty() && cardAdapter.selectableMode){
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
            // TODO send info to adapter about selected state change
            selected = true
            return true
        }
        return false
    }

    fun bind(cardData: Pair<Card, CardCheckResult>, position: Int) {

        selected = cardAdapter.selectedItems.contains(cardData)

        val (card, status) = cardData
        if (card is MpkCard) {
            textViewCardNameId.text = "${card.cardType.label(itemView.context)} \n#${card.clientId}"
        }

        when (status) {
            is CardCheckResult.UnableToGetInformation -> {
                textViewCardStatus.text = "Unknown"
            }
            is CardCheckResult.Expired -> {
                textViewCardStatus.text = "Expired"
            }
            is CardCheckResult.NotExpired -> {
                textViewCardStatus.text = "Valid (${status.daysLeft})"
            }
        }
    }
}
