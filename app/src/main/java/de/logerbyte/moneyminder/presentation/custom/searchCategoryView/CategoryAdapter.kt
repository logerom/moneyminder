package de.logerbyte.moneyminder.presentation.custom.searchCategoryView

import androidx.recyclerview.widget.RecyclerView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.base.adapter.SingleItemTypeAdapter
import de.logerbyte.moneyminder.databinding.FormviewCategoryItemBinding

class CategoryAdapter(private val clickListener: (String) -> Unit) : SingleItemTypeAdapter<String, FormviewCategoryItemBinding>(R.layout.formview_category_item) {
    var originalItems = ArrayList<String>()
        set(value) {
            items = value.toMutableList()
            field = value
        }

    override fun bindItemToView(item: String, bindingClass: FormviewCategoryItemBinding) {
        bindingClass.tvCategoryName.text = item
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.setOnClickListener { clickListener(items[position]) }
    }
}