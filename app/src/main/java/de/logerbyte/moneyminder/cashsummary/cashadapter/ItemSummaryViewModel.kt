package de.logerbyte.moneyminder.cashsummary.cashadapter

import android.databinding.ObservableField

class ItemSummaryViewModel(cost: String) {
    companion object {
        val weekLimit = 70
    }
    
    var _cost = ObservableField<String>()
    var cost: String = cost
        // TODO: 14.03.19 when set cost, create saldo: weekLimit - cost
        set(v) = _cost.set(v)


    var _saldo = ObservableField<String>()
    var saldo: String = "99"
        set(value) = _saldo.set(value)


    var _saving = ObservableField<String>()
    var saving = "999"
        set(value) = _saving.set(value)
}