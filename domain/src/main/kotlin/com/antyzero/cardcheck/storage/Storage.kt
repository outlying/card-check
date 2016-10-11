package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card

/**
 * Representing contract for storing card data
 */
interface Storage {

    fun addCard(card: Card)

    fun removeCard(card: Card)

    fun showCards(): List<Card>
}