package com.antyzero.cardcheck.dsl.extension

fun Long.abs() = if (this < 0) this.times(-1) else this