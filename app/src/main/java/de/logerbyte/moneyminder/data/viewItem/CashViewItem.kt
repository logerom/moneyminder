package de.logerbyte.moneyminder.data.viewItem

import android.os.Build
import de.logerbyte.moneyminder.BuildConfig

data class CashViewItem(
        var cashDate: String = "", var cashName: String = "", var cashAmount: String = "", var cashCategory: String = "",
        var cashPerson: String = ""
){
    init {
        cashDate = if(BuildConfig.DEBUG) "11.11.11" else ""
        cashName = if(BuildConfig.DEBUG) "Burger" else ""
        cashAmount = if(BuildConfig.DEBUG) "1,11" else ""
        cashCategory = if(BuildConfig.DEBUG) "essen" else ""
        cashPerson = if(BuildConfig.DEBUG) "1" else ""
    }
}