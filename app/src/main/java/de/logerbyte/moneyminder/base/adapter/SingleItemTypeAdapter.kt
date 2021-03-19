package de.logerbyte.moneyminder.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class SingleItemTypeAdapter<ITEM_TYPE, BINDING_CLASS : ViewDataBinding>(
        val layout: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun bindItemToView(item: ITEM_TYPE, bindingClass: BINDING_CLASS)

    lateinit var layoutInflater: LayoutInflater
    var items = listOf<ITEM_TYPE>()
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
        (holder as SingleItemTypeAdapter<ITEM_TYPE, BINDING_CLASS>.MyViewHolder).bindItemToView(items[position])
    }
    override fun getItemCount(): Int = items.size

    inner class MyViewHolder(val binding: BINDING_CLASS) : RecyclerView.ViewHolder(binding.root) {
        fun bindItemToView(item: ITEM_TYPE) {
            bindItemToView(item, binding)
        }
    }
}