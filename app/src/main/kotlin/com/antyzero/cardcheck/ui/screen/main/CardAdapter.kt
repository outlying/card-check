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


class CardAdapter(context: Context, val cards: List<Pair<Card, CardCheckResult>>) : RecyclerView.Adapter<CardViewHolder>() {

    private val layoutInflater: LayoutInflater

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false))
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var textViewCardNameId: TextView
    private var textViewCardStatus :TextView
    private var cardIndicator: View

    init {
        textViewCardNameId = itemView.findViewById(R.id.textViewCardNameId) as TextView
        textViewCardStatus = itemView.findViewById(R.id.textViewCardStatus) as TextView
        cardIndicator = itemView.findViewById(R.id.cardIndicator)
    }

    fun bind(cardData: Pair<Card, CardCheckResult>) {
        val (card, status) = cardData

        if (card is MpkCard) {
            textViewCardNameId.text = "${card.cardType.label(itemView.context)} \n#${card.clientId}"
        }

        when(status){
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
