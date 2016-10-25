package com.antyzero.cardcheck.extension

import android.content.Context
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.mpk.Type

/**
 * Return proper label for type value
 */
fun Type.label(context: Context): String {
    return context.getString(when (this) {
        Type.KKM -> R.string.card_kkm
        Type.WZSIB -> R.string.card_wszib
        Type.AGH -> R.string.card_agh
        Type.UJ -> R.string.card_uj
        Type.PK -> R.string.card_pk
        Type.UE -> R.string.card_ue
        Type.UR -> R.string.card_ur
        Type.PWST -> R.string.card_pwst
        Type.AM -> R.string.card_am
        Type.WSE -> R.string.card_wse
        Type.AIK -> R.string.card_aik
        Type.UP -> R.string.card_up
        Type.WSH -> R.string.card_wsh
        Type.KA -> R.string.card_ka
        Type.WSEI -> R.string.card_wsei
        Type.IFJ_PAN -> R.string.card_ifj_pan
        Type.IF_PAN -> R.string.card_if_pan
        Type.IKIFP_PAN -> R.string.card_ikifp_pan
    })
}