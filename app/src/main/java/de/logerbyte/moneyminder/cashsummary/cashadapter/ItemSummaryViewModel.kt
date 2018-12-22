package de.logerbyte.moneyminder.cashsummary.cashadapter

import android.databinding.ObservableField

class ItemSummaryViewModel(cost: Int) {
    var _cost = ObservableField<Int>()
    var cost: Int = cost
        set(v) = _cost.set(v)


    var _saldo = ObservableField<Int>()
    var saldo: Int = 99
        set(value) = _saldo.set(value)


    var _saving = ObservableField<Int>()
    var saving = 999
        set(value) = _saving.set(value)
}