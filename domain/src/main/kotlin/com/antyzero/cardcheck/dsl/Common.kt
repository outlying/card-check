package com.antyzero.cardcheck.dsl


fun Boolean.runIfTrue(body: () -> Unit) {
    if (this) {
        body.invoke()
    }
}

fun Int.abs() = if (this < 0) {
    this.times(-1)
} else {
    this
}