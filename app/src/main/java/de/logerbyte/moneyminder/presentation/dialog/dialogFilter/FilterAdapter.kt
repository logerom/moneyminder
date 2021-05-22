package de.logerbyte.moneyminder.presentation.dialog.dialogFilter

import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.base.adapter.SingleItemTypeAdapter
import de.logerbyte.moneyminder.entities.data.viewData.FilterDialogViewItem
import de.logerbyte.moneyminder.databinding.DialogFilterItemBinding
import javax.inject.Inject

class FilterAdapter @Inject constructor() : SingleItemTypeAdapter<FilterDialogViewItem, DialogFilterItemBinding>(R.layout.dialog_filter_item) {

    override fun bindItemToView(viewItem: FilterDialogViewItem, bindingClass: DialogFilterItemBinding) {
        bindingClass.filterDialogItem = viewItem
    }
}