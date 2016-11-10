package com.antyzero.cardcheck.ui.screen.addcard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.card.mpk.Type
import com.antyzero.cardcheck.dsl.extension.label
import com.antyzero.cardcheck.dsl.extension.layoutInflater
import com.antyzero.cardcheck.dsl.extension.setVisible
import com.antyzero.cardcheck.dsl.extension.startUrl
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.form.EmptyTextViewValidator
import com.antyzero.cardcheck.ui.form.TextViewValidator
import com.antyzero.cardcheck.ui.form.ValidatorCollection
import com.antyzero.cardcheck.ui.form.with
import kotlinx.android.synthetic.main.activity_card_add.*

class AddCardActivity : BaseActivity(), AddCardView, AdapterView.OnItemSelectedListener {

    lateinit private var presenter: AddCardPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_add)
        presenter = AddCardPresenter(this).apply { attachView(this@AddCardActivity) }
        spinnerCardProvider.adapter = CardProviderAdapter()
        spinnerCardProvider.onItemSelectedListener = this

        textViewNumbers.setOnClickListener {
            startUrl("http://www.kkm.krakow.pl/pl/komunikacja/jak-sprawdzic-waznosc/")
        }

        button.setOnClickListener {

            // TODO data valid
            val cardType = spinnerCardProvider.selectedItem as Type
            if (cardType.typeId == Type.KKM.typeId) {

                val validator = ValidatorCollection().with(
                        TextViewValidator(editTextCardId).with(
                                EmptyTextViewValidator()),
                        TextViewValidator(editTextClientId).with(
                                EmptyTextViewValidator()))

                if (validator.isValid().not()) {
                    return@setOnClickListener
                }

                presenter.addCard(MpkCard.Kkm(
                        editTextClientId.text.toString().toInt(),
                        editTextCardId.text.toString().toLong()))
            } else {
                presenter.addCard(MpkCard.Student(
                        editTextClientId.text.toString().toInt(), cardType))
            }
            finish()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (id == Type.KKM.typeId) {
            editTextClientId.setHint(R.string.hint_client_number)
            editTextCardId.setHint(R.string.hint_kkm_card_number)
            editTextCardId.setVisible(true)
        } else {
            editTextClientId.setHint(R.string.hint_album_number)
            editTextCardId.setVisible(false)
        }
    }
}

/**
 * Adapter for spinner
 */
class CardProviderAdapter() : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = getItem(position).label(parent.context)
        return parent.context.layoutInflater()
                .inflate(android.R.layout.simple_list_item_1, parent, false).apply {
            (findViewById(android.R.id.text1) as TextView).text = label
        }
    }

    override fun getItem(position: Int): Type {
        return Type.values()[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).typeId
    }

    override fun getCount(): Int {
        return Type.values().size
    }

}
