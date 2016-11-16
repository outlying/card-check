package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card

/**
 * Representing contract for storing card data
 */
interface PersistentStorage {

    fun addCard(card: Card)

    fun removeCard(card: Card)

    fun getCards(): List<Card>

    fun clean()
}