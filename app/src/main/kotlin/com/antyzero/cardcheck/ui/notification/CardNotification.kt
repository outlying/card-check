package com.antyzero.cardcheck.ui.notification

import android.app.PendingIntent
import android.content.Context
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.NotificationCompat
import com.antyzero.cardcheck.card.Card
import com.antyzero.cardcheck.card.CardCheckResult
import com.antyzero.cardcheck.dsl.extension.notificationManager
import com.antyzero.cardcheck.dsl.extension.pendingActivityIntent
import com.antyzero.cardcheck.ui.screen.main.MainActivity

class CardNotification(private val context: Context) {

    private val notificationManager: NotificationManagerCompat = context.notificationManager()

    /**
     * This is "smart" notification, it will show notification according to card status
     */
    fun cardStatus(card: Card, cardCheckResult: CardCheckResult) {

        when (cardCheckResult) {
            is CardCheckResult.Expired -> cardExpired(card)
            is CardCheckResult.NotExpired -> cardExpiring(card, cardCheckResult)
        }
    }

    fun cardExpired(card: Card) {

        context.notificationBuilder()
                .setContentTitle("Card expired")
                .setContentText("Your card $card is expired")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntentMainActivity())
                .build()
                .let {
                    notificationManager.notify(100 + card.hashCode(), it)
                }
    }

    fun cardExpiring(card: Card, status: CardCheckResult.NotExpired) {

        context.notificationBuilder()
                .setContentTitle("Your card is expiring")
                .setContentText("Your card will last ${status.daysLeft} more days")
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntentMainActivity())
                .build()
                .let {
                    notificationManager.notify(100 + card.hashCode(), it)
                }
    }

    private fun pendingIntentMainActivity(): PendingIntent {
        return context.pendingActivityIntent(0, MainActivity::class.java, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun Context.notificationBuilder() = NotificationCompat.Builder(this)
}