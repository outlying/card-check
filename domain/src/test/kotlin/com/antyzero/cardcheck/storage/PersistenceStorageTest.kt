package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.Type
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.Test
import java.io.File


class PersistenceStorageTest {

    @Test
    fun CollectionAddRestore() {

        // Given
        val fileStorage = FileStorage()

        // When
        fileStorage.addCard(DumbCard())
        fileStorage.addCard(MpkCard.Kkm(2170708, 20603546690))

        // Then
        fileStorage.getCards().size `should be` 2

        val firstCard = fileStorage.getCards()[0]
        (firstCard is DumbCard) `should be` true
        val secondCard = fileStorage.getCards()[1]
        (secondCard is MpkCard.Kkm) `should be` true
        (secondCard as MpkCard.Kkm).cardType `should be` Type.KKM
        secondCard.clientId `should equal` 2170708
        secondCard.cityCardId `should equal` 20603546690
    }

    @Test
    fun Persistency() {

        // Given
        val file = File.createTempFile("someFile", "txt")
        val fileStorage = FileStorage("none", file)
        fileStorage.delete()

        // When
        fileStorage.addCard(DumbCard())
        fileStorage.addCard(MpkCard.Kkm(2170708, 20603546690))

        // Then
        val fileStorageSecond = FileStorage("none", file)
        fileStorageSecond.getCards().size `should be` 2
        (fileStorageSecond.getCards()[0] is DumbCard) `should be` true
        fileStorageSecond.delete()
    }
}