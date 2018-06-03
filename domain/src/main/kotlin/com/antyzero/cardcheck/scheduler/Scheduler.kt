package com.antyzero.cardcheck.scheduler


interface Scheduler {

    fun addSchedule():Boolean

    fun removeAll()
}