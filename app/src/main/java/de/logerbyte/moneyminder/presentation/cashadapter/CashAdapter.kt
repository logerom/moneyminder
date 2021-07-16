package de.logerbyte.moneyminder.presentation.cashadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.data.viewData.ExpenseListViewItem
import de.logerbyte.moneyminder.entities.data.viewData.ExpenseListViewType
import de.logerbyte.moneyminder.entities.data.viewData.SummaryMonthViewItem
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding
import de.logerbyte.moneyminder.databinding.SummaryLineBinding
import de.logerbyte.moneyminder.domain.android.setVisibilityWithFeature
import de.logerbyte.moneyminder.entities.FeatureFlag


/**
 * Created by logerom on 28.07.18.
 */

class CashAdapter(val adapterCallbackV1: AdapterCallbackV1) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var layoutInflater: LayoutInflater
    var items = listOf<ExpenseListViewItem>()
        set(value) {
            field = value
            this.notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is CashViewItem -> ExpenseListViewType.DAY_ITEM.ordinal
            is SummaryMonthViewItem -> ExpenseListViewType.MONTH_ITEM.ordinal
            else -> -1
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        layoutInflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == ExpenseListViewType.DAY_ITEM.ordinal)
            DayExpenseViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.adapter_entry,parent,false))
        else MonthExpenseViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.summary_line,parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder){
            is DayExpenseViewHolder -> {holder.bind(items[position] as CashViewItem)}
            is MonthExpenseViewHolder -> holder.bind(items[position] as SummaryMonthViewItem)
        }
    }

    inner class DayExpenseViewHolder(val binding: AdapterEntryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewItem: CashViewItem){
            binding.vmCashItem = viewItem
            binding.buDelete.setOnClickListener {adapterCallbackV1.onDeleteItemClicked(viewItem) }
            binding.llItem.setOnClickListener { adapterCallbackV1.onItemClicked(viewItem) }
        }
    }

    inner class MonthExpenseViewHolder(val binding: SummaryLineBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(viewItem: SummaryMonthViewItem){
            binding.viewItem = viewItem
            setVisibilityWithFeature(binding.llBudget, FeatureFlag.BUDGET)
            setVisibilityWithFeature(binding.llSaving, FeatureFlag.BUDGET)
        }
    }
}
