package de.logerbyte.moneyminder.presentation.cashadapter

import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem

interface AdapterCallbackV1 {
    fun onDeleteItemClicked(item: CashViewItem)
    fun onItemClicked(viewItem: CashViewItem)
}
