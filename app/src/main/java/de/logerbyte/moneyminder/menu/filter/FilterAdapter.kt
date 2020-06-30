package de.logerbyte.moneyminder.menu.filter

import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.base.adapter.SingleItemTypeAdapter
import de.logerbyte.moneyminder.databinding.DialogFilterItemBinding
import de.logerbyte.moneyminder.menu.filter.FilterDialogItem

class FilterAdapter : SingleItemTypeAdapter<FilterDialogItem, DialogFilterItemBinding>(R.layout.dialog_filter_item) {
    override fun bindItemToView(item: FilterDialogItem, bindingClass: DialogFilterItemBinding) {
        bindingClass.filterDialogItem = item
    }
}