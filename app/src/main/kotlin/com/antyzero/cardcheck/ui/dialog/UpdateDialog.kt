package com.antyzero.cardcheck.ui.dialog

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.antyzero.cardcheck.BuildConfig
import com.antyzero.cardcheck.dsl.extension.checkLatestVersion
import com.antyzero.cardcheck.dsl.extension.logger
import com.antyzero.cardcheck.dsl.extension.show
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpdateDialog : InfoDialog() {

    override fun dataToolbarTitle(): String = "Dostępna nowa wersja"

    override fun dataPositiveButton(): Pair<String, DialogInterface.OnClickListener> {
        return "Zaktualizuj" to DialogInterface.OnClickListener { dialogInterface, i -> }
    }

    override fun dataNegativeButton(): Pair<String, DialogInterface.OnClickListener> {
        return "Nie teraz" to DialogInterface.OnClickListener { dialogInterface, i -> }
    }

    override fun dataContent(): View {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {

        fun showIfUpdatePossible(activity: AppCompatActivity) {

            val logger = activity.logger
            val checkLatestVersion = activity.checkLatestVersion()

            checkLatestVersion.latestVersion(BuildConfig.APPLICATION_ID)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            {
                                if (checkLatestVersion.apkBuildDate().toLocalDate().isBefore(it.first)) {
                                    UpdateDialog().show(activity, "UpdateDialog")
                                }
                            },
                            { logger.w("UpdateDialog", "Unable to show app update dialog", it) }
                    )
        }
    }
}