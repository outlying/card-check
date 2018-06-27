package com.antyzero.cardcheck.dsl.extension

import android.content.Context
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.mpk.MpkCard

/**
 * Return proper label for type value
 */
fun MpkCard.Type.label(context: Context): String {
    return context.getString(when (this) {
        MpkCard.Type.KKM -> R.string.card_kkm
        MpkCard.Type.WZSIB -> R.string.card_wszib
        MpkCard.Type.AGH -> R.string.card_agh
        MpkCard.Type.UJ -> R.string.card_uj
        MpkCard.Type.PK -> R.string.card_pk
        MpkCard.Type.UE -> R.string.card_ue
        MpkCard.Type.UR -> R.string.card_ur
        MpkCard.Type.PWST -> R.string.card_pwst
        MpkCard.Type.AM -> R.string.card_am
        MpkCard.Type.WSE -> R.string.card_wse
        MpkCard.Type.AIK -> R.string.card_aik
        MpkCard.Type.UP -> R.string.card_up
        MpkCard.Type.WSH -> R.string.card_wsh
        MpkCard.Type.KA -> R.string.card_ka
        MpkCard.Type.WSEI -> R.string.card_wsei
        MpkCard.Type.IFJ_PAN -> R.string.card_ifj_pan
        MpkCard.Type.IF_PAN -> R.string.card_if_pan
        MpkCard.Type.IKIFP_PAN -> R.string.card_ikifp_pan
    })
}