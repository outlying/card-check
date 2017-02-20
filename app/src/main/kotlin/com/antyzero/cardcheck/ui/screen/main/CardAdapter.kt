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

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)
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
            if (!value) {
                clearSelected()
            }
        }

    var selectableModeListener: ((Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) = holder.bind(cards[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CardViewHolder(layoutInflater.inflate(R.layout.item_card, parent, false), this, localization)

    override fun getItemCount() = cards.size

    override fun getItemId(position: Int) = cards[position].hashCode().toLong()

    fun clearSelected() {
        selectedItems.clear()
        notifyDataSetChanged()
    }
}

class CardViewHolder(itemView: View, val cardAdapter: CardAdapter, private val localization: Localization) : RecyclerView.ViewHolder(itemView) {

    private val textViewCardNameId: TextView = itemView.findViewById(R.id.textViewCardNameId) as TextView
    private val textViewCardStatus: TextView = itemView.findViewById(R.id.textViewCardStatus) as TextView
    private val cardIndicator: View = itemView.findViewById(R.id.cardIndicator)
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

        itemView.setOnLongClickListener {
            if (!cardAdapter.selectableMode) {
                cardAdapter.selectableMode = true
                selected = true
                true
            } else {
                false
            }
        }

        itemView.setOnClickListener {
            if (cardAdapter.selectableMode) {
                selected = selected.not()
            }
        }
    }

    fun bind(cardData: Pair<Card, CardCheckResult>) {

        selected = cardAdapter.selectedItems.contains(cardData)

        val (card, status) = cardData

        when (card) {
            is MpkCard -> textViewCardNameId.text = "%s \n#%s (%s)".format(card.cardType.label(context), card.cityCardId, card.clientId)
        }

        when (status) {
            is CardCheckResult.UnableToGetInformation -> {
                textViewCardStatus.text = getString(R.string.card_status_unknown)
            }
            is CardCheckResult.Expired -> {
                textViewCardStatus.text = getString(R.string.card_status_expired)
            }
            is CardCheckResult.Valid -> {
                textViewCardStatus.text = "${getString(R.string.card_status_valid)}\n${localization.validFor(status.daysLeft)}"
            }
        }
    }

    private fun getString(@StringRes stringId: Int) = context.getString(stringId)
}
