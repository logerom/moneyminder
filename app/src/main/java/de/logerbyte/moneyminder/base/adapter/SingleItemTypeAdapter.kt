package de.logerbyte.moneyminder.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class SingleItemTypeAdapter<ITEM_TYPE, BINDING_CLASS : ViewDataBinding>(
        val layout: Int,
        val categoryItemViewModel: ItemViewModel<ITEM_TYPE>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var layoutInflater: LayoutInflater
    var items = mutableListOf<ITEM_TYPE>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (this::layoutInflater.isInitialized.not()) layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<BINDING_CLASS>(layoutInflater, layout, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        categoryItemViewModel.setItem(items[position])
    }

    override fun getItemCount(): Int = items.size
}

class MyViewHolder(binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root)
