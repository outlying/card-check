package com.antyzero.cardcheck.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.antyzero.cardcheck.extension.applicationComponent


abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applicationComponent().inject(this)
    }
}