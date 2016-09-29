package com.antyzero.cardcheck.ui

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.antyzero.cardcheck.extension.applicationComponent
import com.antyzero.cardcheck.extension.toast
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity() {

    @Inject lateinit var context:Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent().inject(this)
        context.toast()
    }
}