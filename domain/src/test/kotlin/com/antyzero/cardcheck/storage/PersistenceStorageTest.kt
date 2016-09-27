package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card
import org.amshove.kluent.`should be`
import org.junit.Test


abstract class PersistenceStorageTest(storageFactory: StorageFactory<out Storage>) : StorageTest(storageFactory) {

    @Test
    fun testPersistence() {

        // Given
        val fileName = "persistent.dat"
        storage = storageFactory.create(fileName)
        val card = object : Card() {
            val id = 1
        }
        storage.addCard(card)

        // When
        storage = storageFactory.create(fileName)

        // Then
        storage.showCards().size `should be` 1
        storage.showCards()[0] `should be` card
    }
}