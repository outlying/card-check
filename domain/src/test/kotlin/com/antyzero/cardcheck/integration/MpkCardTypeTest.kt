package com.antyzero.cardcheck.integration

import okhttp3.OkHttpClient
import okhttp3.Request
import org.junit.Test
import java.util.regex.Pattern

class MpkCardTypeTest {

    @Test
    fun testCollectionMatch() {
        val request = Request.Builder().url("http://www.mpk.krakow.pl/pl/").build()
        val response = OkHttpClient().newCall(request).execute().body().string()

        response.println()

        "cityCardType.+?>(.+)</select>".toPattern().matcher(response).apply {
            while (find()){
                System.out.println(group(0))
            }
        }

        val matcher = Pattern.compile("cityCardType.+?>(.+)</select>").matcher(response)
        matcher.find()
        System.out.println(">> ${matcher.group(0)}")
    }
}

fun String.removeLineBreaks():String = this.replace("\n", "")

fun Any.println() = System.out.println(this as String)