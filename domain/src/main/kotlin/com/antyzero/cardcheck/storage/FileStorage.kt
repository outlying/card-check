package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.dumb.DumbCard
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.google.gson.*
import java.io.File
import java.io.OutputStreamWriter
import java.lang.reflect.Type
import java.util.*

class FileStorage(
        private val fileName: String = "storage",
        private val filePath: File = File.createTempFile(fileName, "txt")) : PersistentStorage {

    private val cardsSet: MutableSet<Card> = mutableSetOf()
    private val gson: Gson

    init {
        gson = GsonBuilder()
                .registerTypeHierarchyAdapter(CardList::class.java, Deser())
                .create()

        filePath.apply {
            if (!exists()) {
                createNewFile()
            } else {
                val result = filePath.readLines().joinToString("\n")
                if (!result.isNullOrEmpty()) {
                    cardsSet.addAll(gson.fromJson(result, CardList::class.java))
                    saveSetState()
                }
            }
        }
    }

    override fun addCard(card: Card) {
        cardsSet.add(card)
        saveSetState()
    }

    override fun removeCard(card: Card) {
        cardsSet.remove(card)
        saveSetState()
    }

    override fun getCards(): List<Card> {
        return cardsSet.toList()
    }

    override fun delete() {
        filePath.apply {
            if (exists()) {
                delete()
                createNewFile()
            }
        }
        cardsSet.clear()
    }

    private fun saveSetState() {

        val cardSaveList: MutableSet<CardMeta> = mutableSetOf()
        cardsSet.forEach { cardSaveList.add(wrapWithMeta(it)) }
        val json = gson.toJson(cardSaveList)

        filePath.apply {
            if (exists()) {
                delete()
            }
        }.let {
            OutputStreamWriter(it.outputStream()).use { it.write(json) }
        }
    }

    private fun wrapWithMeta(card: Card): CardMeta {
        return CardMeta(card.javaClass.canonicalName, card)
    }
}

class CardList() : ArrayList<Card>() {

}

data class CardMeta(
        val cardType: String,
        val card: Card) {

}

private class Deser() : JsonDeserializer<CardList> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CardList {
        val cardList = CardList()

        val gson = Gson()

        print(json)
        if (json is JsonArray) {
            json.forEach {
                if (it is JsonObject) {
                    val classCannonical = it.get("cardType").asString
                    val cardElement = it.getAsJsonObject("card")

                    val card = gson.fromJson(cardElement, when (classCannonical) {
                        "com.antyzero.cardcheck.card.dumb.DumbCard" -> DumbCard::class.java
                        "com.antyzero.cardcheck.card.mpk.MpkCard.Kkm" -> MpkCard.Kkm::class.java
                        "com.antyzero.cardcheck.card.mpk.MpkCard.Student" -> MpkCard.Student::class.java
                        else -> throw IllegalArgumentException("Unsupported type")
                    })

                    cardList.add(card)
                }
            }
        }

        return cardList
    }

}