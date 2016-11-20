package com.antyzero.cardcheck.dsl.extension

import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity


fun AppCompatActivity.showDialog(dialogFragment: DialogFragment, tag: String) {
    dialogFragment.show(supportFragmentManager, tag)
}