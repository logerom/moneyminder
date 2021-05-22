package de.logerbyte.moneyminder.presentation.cashadapter

import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewItem

interface AdapterCallbackV1 {
    fun onDeleteItemClicked()
    fun onItemClicked(viewItem: DayExpenseViewItem)
}