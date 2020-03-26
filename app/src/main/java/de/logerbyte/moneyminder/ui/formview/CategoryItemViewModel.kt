package de.logerbyte.moneyminder.ui.formview

import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.base.adapter.ItemViewModel

class CategoryItemViewModel : ItemViewModel<String> {
    var itemName = ObservableField<String>()

    override fun setItem(item: String) {
        itemName.set(item)
    }
}