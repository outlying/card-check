package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable
import java.util.*
import java.util.regex.Pattern

class MpkChecker : Checker<MpkCard> {

    override fun check(card: MpkCard, localDate: LocalDate): Observable<CardCheckResult> {
        return Observable.defer { Observable.just(syncCheck(card, localDate)) }
    }

    private fun syncCheck(card: MpkCard, localDate: LocalDate): CardCheckResult {

        var result = CardCheckResult.Expired

        val okHttpClient = OkHttpClient()
        val httpUrlBuilder = HttpUrl.parse("http://www.mpk.krakow.pl/pl/sprawdz-waznosc-biletu/index,1.html").newBuilder()

        httpUrlBuilder.setEncodedQueryParameter("identityNumber", card.clientId.toString())
        httpUrlBuilder.setEncodedQueryParameter("cityCardType", card.cardType.typeId.toString())
        httpUrlBuilder.setEncodedQueryParameter("dateValidity", "${localDate.year}-${localDate.monthValue}-${localDate.dayOfMonth}")

        if (card is MpkCard.Kkm) {
            httpUrlBuilder.setEncodedQueryParameter("cityCardNumber", card.cityCardId.toString())
        }

        val request = Request.Builder()
                .url(httpUrlBuilder.build())
                .build()

        try {

            val webSource = okHttpClient.newCall(request).execute().body().string()

            "<!-- Index:Begin -->(.+)<!-- Index:End -->".toPattern(Pattern.DOTALL)
                    .matcher(webSource).run {
                if (!find()) {
                    return@run
                }

                "Rodzaj biletu.+?Data pocz.+?([\\d-]+).+?Data ko.+?([\\d-]+).+?Linie strefowe".toPattern(Pattern.DOTALL)
                        .matcher(group(0)).run {

                    val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())

                    while (find()) {

                        val d1 = group(1).trim()
                        val d2 = group(2).trim()

                        val startDate = LocalDate.parse(d1, formatter)
                        val endDate = LocalDate.parse(d2, formatter)

                        if (localDate.isBefore(endDate) && localDate.isAfter(startDate)) {
                            result = CardCheckResult.NotExpired
                        }
                    }
                }
            }
        } catch (e: Exception) {
            result = CardCheckResult.UnableToGetInformation
        }

        return result
    }
}