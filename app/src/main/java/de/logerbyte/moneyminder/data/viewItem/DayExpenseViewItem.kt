package de.logerbyte.moneyminder.data.viewItem

import android.text.Editable
import androidx.databinding.ObservableField

data class DayExpenseViewItem(val cashDate: String, val cashName: String, val cashAmount: String, val cashCategory: String,
                              val cashPerson: String): ExpenseListViewItem {
    var newDateText = ""
    var dateDotDelete = false

    fun onDateTextChanged(s: Editable) {
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

    fun isAllSet(): Boolean {
        return !(isSomeElementNull(listOf(cashDate, cashName, cashCategory, cashAmount, cashPerson)))
    }

    private fun isSomeElementNull(listOf: List<String?>): Boolean {
        for (element in listOf) {
            if (element.isNullOrEmpty()) {
                return true
            }
        }
        return false
    }
}