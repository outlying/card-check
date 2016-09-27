package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card
import org.amshove.kluent.`should be`
import org.junit.Before
import org.junit.Test

/**
 * Storage test, use to test your implementation when in doubt
 */
abstract class StorageTest(val storageFactory: StorageFactory<out Storage>) {

    lateinit var storage: Storage

    @Before
    fun setUp() {
        storage = storageFactory.create()
    }

    @Test
    fun testAddCardToStorage() {

        // Given
        val card = object : Card() {}

        // When
        storage.addCard(card)

        // Then
        storage.showCards().size `should be` 1
        storage.showCards()[0] `should be` card
    }

    @Test
    fun testRemoveCard() {

        // Given
        val card = object : Card() {}
        storage.addCard(card)

        // When
        storage.removeCard(card)

        // Then
        storage.showCards().isEmpty() `should be` true
    }

    @Test
    fun testDisallowDuplicates() {

        // Given
        val card = object : Card() {}

        // When
        storage.addCard(card)
        storage.addCard(card)
        storage.addCard(card)

        // Then
        storage.showCards().size `should be` 1
    }

    @Test
    fun testProperSizeAfterOperations() {

        // Given & When
        storage.addCard(object : Card() {})
        storage.addCard(object : Card() {})
        storage.addCard(object : Card() {})

        // Then
        storage.showCards().size `should be` 3
    }
}