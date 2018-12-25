package de.logerbyte.moneyminder.cashsummary.cashadapter

import android.databinding.ObservableField

class ItemSummaryViewModel(cost: String) {
    var _cost = ObservableField<String>()
    var cost: String = cost
        set(v) = _cost.set(v)


    var _saldo = ObservableField<String>()
    var saldo: String = "99"
        set(value) = _saldo.set(value)


    var _saving = ObservableField<String>()
    var saving = "999"
        set(value) = _saving.set(value)
}