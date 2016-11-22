package com.antyzero.cardcheck.version

import android.content.Context
import com.antyzero.cardcheck.BuildConfig
import com.antyzero.cardcheck.dsl.extension.println
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import rx.Observable
import java.util.*

class CheckLatestVersion(val okHttpClient: OkHttpClient = OkHttpClient()) {

    fun latestVersion(applicationId: String): Observable<Pair<LocalDate, String?>> {
        return Observable.defer { Observable.just(syncLatestVersion(applicationId)) }
    }

    fun apkBuildDate(): LocalDateTime {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(BuildConfig.BUILD_DATE), ZoneOffset.systemDefault())
    }

    private fun syncLatestVersion(applicationId: String): Pair<LocalDate, String?> {

        val httpUrl = HttpUrl.parse("https://play.google.com/store/apps/details?hl=en")
                .newBuilder()
                .setEncodedQueryParameter("id", applicationId)
                .build()

        val request = Request.Builder().apply {
            url(httpUrl)
        }.build()

        return okHttpClient.newCall(request).execute().body().string().let {

            val localDate = "itemprop=\"datePublished\">(.+?)</div>".toPattern().matcher(it).run {
                if (find()) {
                    return@run LocalDate.parse(group(1).trim(), DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH))
                }
                throw IllegalStateException("Unable to find update date for $applicationId")
            }

            val version: String? = "itemprop=\"softwareVersion\">(.+?)</div>".toPattern().matcher(it).run {
                if (find()) {
                    return@run group(1).trim()
                }
                return@run null
            }

            localDate to version
        }

        // <div class="content" itemprop="datePublished">November 6, 2016</div>
        // <div class="content" itemprop="softwareVersion"> 1.19.29  </div>
        // https://play.google.com/store/apps/details?id=com.google.android.apps.chromecast.app&hl=en
    }
}