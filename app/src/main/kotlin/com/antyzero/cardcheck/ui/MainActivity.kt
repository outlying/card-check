package com.antyzero.cardcheck.ui

import android.os.Bundle
import com.antyzero.cardcheck.CardCheck
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.extension.applicationComponent
import com.antyzero.cardcheck.extension.toast
import org.threeten.bp.LocalDate
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject lateinit var cardCheck: CardCheck

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        applicationComponent().inject(this)

        val card = MpkCard.Kkm(2170708, 20603546690)
        val localDate = LocalDate.now()

        cardCheck.check(card, localDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { this.toast(it.toString()) }
    }
}