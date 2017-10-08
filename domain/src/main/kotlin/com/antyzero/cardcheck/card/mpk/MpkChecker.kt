package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import rx.Observable
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MpkChecker(
        private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()) : Checker<MpkCard> {

    override fun check(card: MpkCard, localDate: LocalDate): Observable<CardCheckResult> {
        return Observable.defer { Observable.just(syncCheck(card, localDate)) }
    }

    private fun syncCheck(card: MpkCard, localDate: LocalDate): CardCheckResult {

        var result: CardCheckResult = CardCheckResult.Expired()

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
                    val ranges: MutableList<Pair<LocalDate, LocalDate>> = mutableListOf()

                    while (find()) {

                        val d1 = group(1).trim()
                        val d2 = group(2).trim()

                        val startDate = LocalDate.parse(d1, formatter)
                        val endDate = LocalDate.parse(d2, formatter)

                        ranges.add(startDate to endDate)
                    }

                    ranges.sortBy { it.first }

                    ranges.reduceIf(
                            { earlier, later -> earlier.second.until(later.first, ChronoUnit.DAYS).toInt().abs() == 1 },
                            { earlier, later -> earlier.first to later.second })

                    ranges.forEach {
                        val (startDate, endDate) = it

                        if ((localDate.isBefore(endDate) || localDate.isEqual(endDate)) && (localDate.isAfter(startDate) || localDate.isEqual(startDate))) {
                            result = CardCheckResult.Valid(localDate.until(endDate, ChronoUnit.DAYS).toInt())
                        }
                    }
                }
            }
        } catch (e: Exception) {
            result = CardCheckResult.UnableToGetInformation(e)
        }

        return result
    }
}

fun <E> MutableList<E>.reduceIf(condition: (E, E) -> Boolean, reduction: (E, E) -> E) {

    do {
        var changed = false
        for ((i, e) in withIndex()) {
            if (i + 1 < size) {
                val next = get(i + 1)
                if (condition(e, next)) {
                    set(i, reduction(e, next))
                    removeAt(i + 1)
                    changed = true
                    break
                }
            }
        }
    } while (changed)
}

fun Int.abs() = if (this < 0) {
    this.times(-1)
} else {
    this
}