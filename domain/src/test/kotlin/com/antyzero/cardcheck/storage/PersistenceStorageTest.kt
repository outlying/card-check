package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.Type
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Test
import java.io.File


class PersistenceStorageTest {

    @Test
    fun collectionAddRestore() {

        // Given
        val fileStorage = FileStorage()

        // When
        fileStorage.addCard(MpkCard.Kkm(2170708, 20603546690))

        // Then
        fileStorage.getCards().size `should be` 1

        val secondCard = fileStorage.getCards()[0]
        (secondCard is MpkCard.Kkm) `should be` true
        (secondCard as MpkCard.Kkm).cardType `should be` Type.KKM
        secondCard.clientId `should equal` 2170708
        secondCard.cityCardId `should equal` 20603546690
    }

    @Test
    fun noDuplicates() {

        // Given
        val fileStorage = FileStorage().apply { delete() }

        // When
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))

        // Then
        fileStorage.getCards().size `should be` 1
    }

    @Test
    fun noDuplicatesInStorage() {

        // Given
        val file = File.createTempFile("dupliations", "txt")
        val fileStorage = FileStorage("todoChange", file).apply { delete() }

        // When
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))
        fileStorage.addCard(MpkCard.Kkm(1234, 41234))

        // Then
        val fileStorageSecond = FileStorage("todoStupid", file)
        fileStorageSecond.getCards().size `should be` 1

    }

    @Test
    fun Persistency() {

        // Given
        val file = File.createTempFile("someFile", "txt")
        val fileStorage = FileStorage("none", file)
        fileStorage.delete()

        // When
        fileStorage.addCard(MpkCard.Kkm(2170708, 20603546690))

        // Then
        val fileStorageSecond = FileStorage("none", file)
        fileStorageSecond.getCards().size `should be` 1
        fileStorageSecond.delete()
    }
}