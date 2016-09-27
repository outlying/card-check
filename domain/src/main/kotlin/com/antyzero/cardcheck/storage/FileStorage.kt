package com.antyzero.cardcheck.storage

import com.antyzero.cardcheck.card.Card
import java.io.File

class FileStorage(private val fileName: String = "storage") : Storage {

    private val cardsSet: MutableSet<Card> = mutableSetOf()

    init {
        // TODO restore state from file if possible

        file().apply {
            if (!exists()) {
                createNewFile()
            }
        }.apply { }
    }

    override fun addCard(card: Card) {
        cardsSet.add(card)
        saveSetState()
    }

    override fun removeCard(card: Card) {
        cardsSet.remove(card)
        saveSetState()
    }

    override fun showCards(): List<Card> {
        return cardsSet.toList()
    }

    fun delete() {
        file().apply {
            if (exists()) {
                delete()
            }
        }
    }

    private fun saveSetState() {

        file().apply {
            if (exists()) {
                delete()
            }
            createNewFile()
        }.printWriter().use { out ->
            cardsSet.forEach {
                val s = "${it.javaClass}|$it"
                out.println(s)
            }
        }
    }

    private fun file(): File = File.createTempFile(fileName, "dat")
}