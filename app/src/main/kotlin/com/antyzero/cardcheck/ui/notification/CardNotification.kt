package com.antyzero.cardcheck.ui.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.support.annotation.StringRes
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.NotificationCompat
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.dsl.extension.notificationManager
import com.antyzero.cardcheck.dsl.extension.pendingActivityIntent
import com.antyzero.cardcheck.localization.Localization
import com.antyzero.cardcheck.ui.screen.main.MainActivity

class CardNotification(private val context: Context, private val localization: Localization) {

    private val notificationManager: NotificationManagerCompat = context.notificationManager()

    /**
     * This is "smart" notification, it will showIfRequired notification according to card status
     */
    fun cardStatus(card: Card, cardCheckResult: CardCheckResult) {

        when (cardCheckResult) {
            is CardCheckResult.Expired -> cardExpired(card)
            is CardCheckResult.Valid -> cardExpiring(card, cardCheckResult)
        }
    }

    fun cardExpired(card: Card) {

        context.notificationBuilder()
                .setContentTitle(getString(R.string.card_expired))
                .setContentText(context.getString(R.string.card_expired_message, card))
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntentMainActivity())
                .build()
                .let {
                    notificationManager.notify(card, it)
                }
    }

    fun cardExpiring(card: Card, status: CardCheckResult.Valid) {

        context.notificationBuilder()
                .setContentTitle(getString(R.string.card_expiring))
                .setContentText(localization.cardLastDays(status.daysLeft))
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setGroup("CARD_EXPIRING")
                .setContentIntent(pendingIntentMainActivity())
                .build()
                .let {
                    notificationManager.notify(card, it)
                }
    }

    private fun pendingIntentMainActivity(): PendingIntent {
        return context.pendingActivityIntent(0, MainActivity::class.java, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun Context.notificationBuilder() = NotificationCompat.Builder(this)

    private fun getString(@StringRes stringId: Int) = this.context.getString(stringId)

    private fun NotificationManagerCompat.notify(card: Card, notification: Notification) {
        this.notify(1000 + card.hashCode(), notification)
    }
}