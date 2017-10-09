package com.antyzero.cardcheck.dsl


fun Boolean.runIfTrue(body: () -> Unit) {
    if (this) {
        body.invoke()
    }
}