package de.logerbyte.moneyminder.presentation.cashadapter

import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem

interface AdapterCallbackV1 {
    fun onDeleteItemClicked()
    fun onItemClicked(viewItem: DayExpenseViewItem)
}