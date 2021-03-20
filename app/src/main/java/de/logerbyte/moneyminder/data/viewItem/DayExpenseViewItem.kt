package de.logerbyte.moneyminder.data.viewItem

import android.text.Editable
import androidx.databinding.ObservableField

data class DayExpenseViewItem(val cashDate: String, val cashName: String, val cashAmount: String, val cashCategory: String,
                              val cashPerson: String): ExpenseListViewItem {

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