package com.antyzero.cardcheck.tracker

interface Tracker {

    fun unableToShowNotification(throwable: Throwable)
}