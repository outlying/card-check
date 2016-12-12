package com.antyzero.cardcheck.tracker


/**
 * Will call corresponding methods on all passed trackers
 */
class AggregationTracker(private val trackers: List<Tracker>) : Tracker {

    constructor(vararg trackers: Tracker) : this(trackers.asList())

    override fun unableToShowNotification(throwable: Throwable) = trackers.forEach {
        it.unableToShowNotification(throwable)
    }
}