package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.network.Requester
import io.reactivex.Flowable
import okhttp3.HttpUrl
import org.threeten.bp.LocalDate

class RequesterMpkSites(private val requester: Requester) : MpkSites {

    override fun cardStatus(card: MpkCard, localDate: LocalDate): Flowable<String> {

        val httpUrlBuilder = HttpUrl.parse("http://www.mpk.krakow.pl/pl/sprawdz-waznosc-biletu/index,1.html")!!.newBuilder()

        httpUrlBuilder.apply {
            val date = "${localDate.year}-${localDate.monthValue}-${localDate.dayOfMonth}"
            setEncodedQueryParameter("identityNumber", card.clientId.toString())
            setEncodedQueryParameter("cityCardType", card.cardType.typeId.toString())
            setEncodedQueryParameter("dateValidity", date)
        }

        if (card is MpkCard.Kkm) {
            httpUrlBuilder.setEncodedQueryParameter("cityCardNumber", card.cityCardId.toString())
        }

        val url = httpUrlBuilder.build()

        return requester.get(url.toString())
    }
}


interface MpkSites {

    fun cardStatus(card: MpkCard, localDate: LocalDate): Flowable<String>
}