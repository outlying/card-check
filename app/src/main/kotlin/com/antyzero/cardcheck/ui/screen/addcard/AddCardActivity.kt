package com.antyzero.cardcheck.ui.screen.addcard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.extension.layoutInflater
import com.antyzero.cardcheck.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_card_add.*

class AddCardActivity : BaseActivity(), AddCardView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_add)
        spinnerCardProvider.adapter = CardProviderAdapter()
    }
}

/**
 * Adapter for spinner
 */
class CardProviderAdapter() : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return parent.context.layoutInflater()
                .inflate(android.R.layout.simple_list_item_1, parent, false).apply {
            (findViewById(android.R.id.text1) as TextView).text = getItem(position).name // TODO change name
        }
    }

    override fun getItem(position: Int): Provider {
        return Provider.values()[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).ordinal.toLong()
    }

    override fun getCount(): Int {
        return Provider.values().size
    }

}

/**
 * List of supported card providers
 */
enum class Provider {
    MPK
}
