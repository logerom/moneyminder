package de.logerbyte.moneyminder.cashOverview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.logerbyte.moneyminder.presentation.cashadapter.CashAdapter

@BindingAdapter("recyclerAdapter")
fun setAdapter(recyclerView: RecyclerView, cashAdapter: CashAdapter) {
    recyclerView.adapter = cashAdapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
}