package com.antyzero.cardcheck.mvp


interface Presenter<T> where T : View {

    fun attachView(view: T)
}