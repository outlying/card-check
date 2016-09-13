package com.antyzero.cardcheck.integration

import com.antyzero.cardcheck.card.mpk.Type
import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import java.util.regex.Pattern

class MpkCardTypeTest {

    /**
     * Check if current enum set match the one on MPK site
     */
    @Test
    fun testCollectionMatch() {
        val request = Request.Builder().url("http://www.mpk.krakow.pl/pl/").build()
        val response = OkHttpClient().newCall(request).execute().body().string()

        "cityCardType.+?>(.+)</select>".toPattern(Pattern.DOTALL).matcher(response).run {
            if (!find()) throw IllegalStateException("Missing data")

            "value=\"(\\d+)\">(.+?)<".toPattern().matcher(group(0)).run {
                while (find()) {
                    val cardData = group(1).toInt() to group(2)
                    try {
                        Type.findByTypeId(cardData.first)
                    } catch (e: Exception) {
                        throw IllegalStateException("Missing enum for $cardData", e)
                    }
                }
            }
        }
    }
}