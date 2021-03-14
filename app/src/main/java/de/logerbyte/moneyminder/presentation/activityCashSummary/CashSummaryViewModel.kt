package de.logerbyte.moneyminder.presentation.activityCashSummary

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.presentation.cashadapter.CashAdapter
import de.logerbyte.moneyminder.domain.ExpenseDataManager
import de.logerbyte.moneyminder.data.viewItem.WeekSummaryViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.mapper.WeekSummaryItemMapper
import de.logerbyte.moneyminder.domain.util.DigitUtil.dotToComma
import de.logerbyte.moneyminder.domain.util.DigitUtil.doubleToString
import de.logerbyte.moneyminder.domain.util.DigitUtil.getCashTotal
import java.text.SimpleDateFormat
import javax.inject.Inject

class CashSummaryViewModel @Inject constructor(val expenseDataManager: ExpenseDataManager,
                                               val mapper: WeekSummaryItemMapper,
                                               val sdf: SimpleDateFormat) : ViewModel(), CashAdapter.Listener {
    val totalExpenses = ObservableField<Double>()
    private val totalBudget = ObservableField("0,00")
    private val totalSaving = ObservableField<Double?>()
    private val _cashItems = MutableLiveData<List<WeekSummaryViewItem>>()
    val cashItems: LiveData<List<WeekSummaryViewItem>> = _cashItems

    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"
    init {
        totalExpenses.set(0.0)
        initCashViewItem()
    }

    private fun initCashViewItem() {
        expenseDataManager.loadExpenseList()
            .map { it.sortedBy { sdf.parse(it.cashDate) } }
            .map { mapper.map(it) }
            .subscribe {_cashItems.value = it}
    }

    private fun addCashToTotal(cashList: List<Expense>) {
        totalExpenses.set(getCashTotal(cashList))
    }

    fun getTotalBudget(): ObservableField<String> {
        totalBudget.set(dotToComma(totalBudget.get()!!))
        return totalBudget
    }

    fun getTotalSaving(): ObservableField<Double?> {
        totalSaving.set(totalSaving.get())
        return totalSaving
    }

    override fun onLoadedExpenses(expenses: List<Expense>, allBudget: Int) {
        addCashToTotal(expenses)
        totalBudget.set(doubleToString(allBudget.toDouble()))
        totalSaving.set(java.lang.Double.valueOf(allBudget.toDouble()) - totalExpenses.get()!!)
    }
}