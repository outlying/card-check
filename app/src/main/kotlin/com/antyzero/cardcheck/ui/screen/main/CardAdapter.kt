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
    private var pSelectableMode: Boolean = false

    var selectableModeListener: ((Boolean) -> Unit)? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cards[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false), this)
    }

    override fun getItemCount(): Int {
        return cards.size
    }

    fun setSelectableMode(value: Boolean) {
        selectableModeListener?.invoke(value)
        pSelectableMode = value
    }

    fun getSelectableMode(): Boolean {
        return pSelectableMode
    }

}

class CardViewHolder(itemView: View, val cardAdapter: CardAdapter) : RecyclerView.ViewHolder(itemView), View.OnLongClickListener {

    private val textViewCardNameId: TextView
    private val textViewCardStatus: TextView
    private val cardIndicator: View

    init {
        textViewCardNameId = itemView.findViewById(R.id.textViewCardNameId) as TextView
        textViewCardStatus = itemView.findViewById(R.id.textViewCardStatus) as TextView
        cardIndicator = itemView.findViewById(R.id.cardIndicator)
    }

    override fun onLongClick(v: View?): Boolean {
        if(cardAdapter.getSelectableMode() == false){
            cardAdapter.setSelectableMode(true)
            return true
        }
        return false
    }

    fun bind(cardData: Pair<Card, CardCheckResult>) {
        itemView.setOnLongClickListener(this)

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
