package com.antyzero.cardcheck.tracker.answers

import com.antyzero.cardcheck.tracker.Tracker
import com.crashlytics.android.answers.Answers

class AnswersTracker(private val answers: Answers) : Tracker {

    override fun unableToShowNotification(throwable: Throwable) {
        // do nothing
    }
}