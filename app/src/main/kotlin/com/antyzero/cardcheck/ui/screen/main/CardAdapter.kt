package com.antyzero.cardcheck.ui.screen.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        return CardViewHolder(layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false))
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var textView: TextView

    init {
        textView = itemView.findViewById(android.R.id.text1) as TextView
    }

    fun bind(cardData: Pair<Card, CardCheckResult>) {
        val (card, status) = cardData

        if (card is MpkCard){
            textView.text = "MPK, ${card.cardType.label(itemView.context)} ${card.clientId}, $status"
        }
    }
}
