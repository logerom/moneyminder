package de.logerbyte.moneyminder.addCashDialog

import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.base.adapter.SingleItemTypeAdapter
import de.logerbyte.moneyminder.databinding.FormviewCategoryItemBinding

class CategoryAdapter : SingleItemTypeAdapter<String, FormviewCategoryItemBinding>(R.layout.formview_category_item) {
    override fun binds(item: String, bindingClass: FormviewCategoryItemBinding) {
        bindingClass.tvCategoryName.text = item
    }
}