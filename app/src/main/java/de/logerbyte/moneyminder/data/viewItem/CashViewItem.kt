package de.logerbyte.moneyminder.data.viewItem

import android.os.Build
import de.logerbyte.moneyminder.BuildConfig

@Deprecated(message = "Use DayExpenseViewItem")
data class CashViewItem(
        var cashDate: String = "", var cashName: String = "", var cashAmount: String = "", var cashCategory: String = "",
        var cashPerson: String = ""){

    init {
        cashDate = if(BuildConfig.DEBUG && cashDate.isBlank()) "11.11.11" else cashDate
        cashName = if(BuildConfig.DEBUG && cashName.isBlank()) "Burger" else cashName
        cashAmount = if(BuildConfig.DEBUG && cashAmount.isBlank()) "1,11" else cashAmount
        cashCategory = if(BuildConfig.DEBUG && cashCategory.isBlank()) "essen" else cashCategory
        cashPerson = if(BuildConfig.DEBUG && cashPerson.isBlank()) "1" else cashPerson
    }
}