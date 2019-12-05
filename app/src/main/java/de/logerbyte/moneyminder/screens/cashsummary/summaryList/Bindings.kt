package de.logerbyte.moneyminder.screens.cashsummary.summaryList

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter

@BindingAdapter("recyclerAdapter")
fun setAdapter(recyclerView: RecyclerView, cashAdapter: CashAdapter) {
    recyclerView.adapter = cashAdapter
    recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
}