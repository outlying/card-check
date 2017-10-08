package com.antyzero.cardcheck.card.mpk

import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.card.Checker
import io.reactivex.Observable
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MpkChecker(
        private val okHttpClient: OkHttpClient = defaultClient()) : Checker<MpkCard> {

    override fun check(card: MpkCard, localDate: LocalDate): Observable<CardCheckResult> {
        return Observable.defer { Observable.just(syncCheck(card, localDate)) }
    }

    private fun syncCheck(card: MpkCard, localDate: LocalDate): CardCheckResult {

        var result: CardCheckResult = CardCheckResult.UnableToGetInformation()

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

        val request = Request.Builder()
                .url(httpUrlBuilder.build())
                .build()

        try {

            val webSource = okHttpClient.newCall(request).execute().body()!!.string()

            "<!-- Index:Begin -->(.+)<!-- Index:End -->".toPattern(Pattern.DOTALL)
                    .matcher(webSource).run {

                if (!find()) {
                    return@run
                }

                result = analiseSource(result, group(0), localDate)
            }
        } catch (e: Exception) {
            result = CardCheckResult.UnableToGetInformation(e)
        }

        return result
    }

    /**
     * Look up for valid range
     */
    private fun analiseSource(initialResult: CardCheckResult, source: String, localDate: LocalDate): CardCheckResult {
        var returningResult = initialResult

        "Rodzaj biletu.+?Data pocz.+?([\\d-]+).+?Data ko.+?([\\d-]+).+?Linie strefowe".toPattern(Pattern.DOTALL)
                .matcher(source).run {

            val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault())
            val ranges: MutableList<Pair<LocalDate, LocalDate>> = mutableListOf()

            while (find()) {

                val d1 = group(1).trim()
                val d2 = group(2).trim()

                val startDate = LocalDate.parse(d1, formatter)
                val endDate = LocalDate.parse(d2, formatter)

                ranges.add(startDate to endDate)
            }

            if (ranges.isEmpty()) {
                returningResult = CardCheckResult.Expired()
                return@run
            }

            ranges.sortBy { it.first }
            ranges.reduceIf(
                    { earlier, (later) -> earlier.second.until(later, ChronoUnit.DAYS).toInt().abs() == 1 },
                    { (earlier), later -> earlier to later.second })

            ranges.forEach { (startDate, endDate) ->
                if ((localDate.isBefore(endDate) || localDate.isEqual(endDate)) && (localDate.isAfter(startDate) || localDate.isEqual(startDate))) {
                    returningResult = CardCheckResult.Valid(localDate.until(endDate, ChronoUnit.DAYS).toInt())
                }
            }
        }
        return returningResult
    }
}

private fun defaultClient(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

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