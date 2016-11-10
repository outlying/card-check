package com.antyzero.cardcheck.dsl.extension

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.temporal.ChronoUnit


fun ChronoUnit.betweenWithMidnight(first: LocalTime, second: LocalTime): Long {
    val firstDateTime = first.atDate(LocalDate.MIN)
    var secondDateTime = second.atDate(LocalDate.MIN)

    if (secondDateTime.isBefore(firstDateTime)) {
        secondDateTime = secondDateTime.plusDays(1)
    }

    return ChronoUnit.SECONDS.between(firstDateTime, secondDateTime)
}