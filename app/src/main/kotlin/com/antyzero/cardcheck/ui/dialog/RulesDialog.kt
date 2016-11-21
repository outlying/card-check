package com.antyzero.cardcheck.ui.dialog

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Html.fromHtml
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.dsl.extension.layoutInflater
import com.antyzero.cardcheck.dsl.extension.showDialog
import com.antyzero.cardcheck.dsl.extension.tag


class RulesDialog : InfoDialog() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        isCancelable = false
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun dataToolbarTitle(): String {
        return getString(R.string.rules_dialog_title)
    }

    override fun dataPositiveButton(): Pair<String, DialogInterface.OnClickListener> {
        return getString(R.string.understand_and_accept) to DialogInterface.OnClickListener { dialogInterface, i ->
            context.sharedPreferences().acceptRules()
        }
    }

    override fun dataNegativeButton(): Pair<String, DialogInterface.OnClickListener> {
        return getString(R.string.decline) to DialogInterface.OnClickListener { dialogInterface, i ->
            activity.let {
                it.finish()
            }
        }
    }

    override fun dataContent(): View {
        return layoutInflater().inflate(R.layout.dialog_rules, null).apply {

            (findViewById(R.id.textViewRulesDescription) as TextView).apply {
                text = fromHtml(getString(R.string.rules_description))
                movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }

    companion object {

        private val key = "KEY_ACCEPTED"

        fun showIfRequired(appCompatActivity: AppCompatActivity) {
            if (appCompatActivity.sharedPreferences().areRulesAccepted().not()) {
                val dialog = RulesDialog()
                appCompatActivity.showDialog(dialog, dialog.tag())
            }
        }

        private fun Context.sharedPreferences(): SharedPreferences {
            return getSharedPreferences(tag(), Context.MODE_PRIVATE)
        }

        private fun SharedPreferences.acceptRules() {
            edit().putBoolean(key, true).commit()
        }

        private fun SharedPreferences.areRulesAccepted(): Boolean {
            return getBoolean(key, false)
        }
    }
}