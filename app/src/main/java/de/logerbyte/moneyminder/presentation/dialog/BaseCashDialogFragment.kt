package de.logerbyte.moneyminder.presentation.dialog

import android.os.Parcelable
import android.text.Editable

abstract class BaseCashDialogFragment<PARCEL_TYPE: Parcelable>: BaseDialogFragment<PARCEL_TYPE>(), CashViewAction{

    var newDateText = ""
    var dateDotDelete = false

    // todo X: base method for changing text in ViewModel?
    override fun onDateTextChanged(s: Editable) {
        if (dateDotDelete) {
            dateDotDelete = false
            return
        }
        // fixme: 02.09.18 make util class with that dot delete logic

        if (s.length < newDateText.length) {
            val charAt = newDateText.toString().get(newDateText.toString().length - 1)
            if (charAt == '.') {
                dateDotDelete = true
                s.delete(s.toString().length - 1, s.toString().length)
            }
            newDateText = s.toString()
            return
        }

        if (s.toString().length == 2 || s.toString().length == 5) {
            s.append(".")
            newDateText = s.toString()
            return
        }
        newDateText = s.toString()
    }

}

