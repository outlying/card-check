package com.antyzero.cardcheck.dsl.extension


@Suppress("PropertyName")
val Any.TAG: String
    get() = this.javaClass.simpleName