package com.antyzero.cardcheck.ui.screen.addcard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.TextView
import com.antyzero.cardcheck.R
import com.antyzero.cardcheck.card.mpk.MpkCard
import com.antyzero.cardcheck.dsl.extension.browse
import com.antyzero.cardcheck.dsl.extension.label
import com.antyzero.cardcheck.dsl.extension.layoutInflater
import com.antyzero.cardcheck.dsl.extension.setVisible
import com.antyzero.cardcheck.ui.BaseActivity
import com.antyzero.cardcheck.ui.form.EmptyTextViewValidator
import com.antyzero.cardcheck.ui.form.TextViewValidator
import com.antyzero.cardcheck.ui.form.ValidatorCollection
import com.antyzero.cardcheck.ui.form.with
import com.antyzero.cardcheck.ui.inputfilter.OnlyDigitsInputFilter
import com.antyzero.cardcheck.ui.inputfilter.OnlyPositiveIntInputFilter
import com.antyzero.cardcheck.ui.inputfilter.OnlyPositiveLongInputFilter
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
            browse("http://www.kkm.krakow.pl/pl/komunikacja/jak-sprawdzic-waznosc/")
        }

        editTextClientId.filters = arrayOf(
                OnlyDigitsInputFilter,
                OnlyPositiveIntInputFilter)

        editTextCardId.filters = arrayOf(
                OnlyDigitsInputFilter,
                OnlyPositiveLongInputFilter)

        button.setOnClickListener {

            // TODO data valid
            val cardType = spinnerCardProvider.selectedItem as MpkCard.Type
            if (cardType.typeId == MpkCard.Type.KKM.typeId) {

                val validator = ValidatorCollection().with(
                        TextViewValidator(editTextCardId).with(
                                EmptyTextViewValidator()),
                        TextViewValidator(editTextClientId).with(
                                EmptyTextViewValidator()))

                if (validator.isValid().not()) {
                    return@setOnClickListener
                }

                presenter.addCard(MpkCard.Kkm(
                        editTextClientId.text.toString().toLong(),
                        editTextCardId.text.toString().toLong()))
            } else {
                presenter.addCard(MpkCard.Student(
                        editTextClientId.text.toString().toLong(), cardType))
            }
            finish()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // do nothing
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (id == MpkCard.Type.KKM.typeId) {
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
class CardProviderAdapter : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val label = getItem(position).label(parent.context)
        return parent.context.layoutInflater()
                .inflate(android.R.layout.simple_list_item_1, parent, false).apply {
                    findViewById<TextView>(android.R.id.text1).text = label
                }
    }

    override fun getItem(position: Int): MpkCard.Type {
        return MpkCard.Type.values()[position]
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).typeId
    }

    override fun getCount(): Int {
        return MpkCard.Type.values().size
    }

}
