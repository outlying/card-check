package com.antyzero.cardcheck.extension

import android.widget.Toast

fun Any?.println() = System.out.println(this)

fun Any.tag() = this.javaClass.simpleName