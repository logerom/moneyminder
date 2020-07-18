package de.logerbyte.moneyminder.ui.summaryList

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel.ActivityListener
import de.logerbyte.moneyminder.util.DigitUtil.dotToComma
import de.logerbyte.moneyminder.util.DigitUtil.doubleToString
import de.logerbyte.moneyminder.util.DigitUtil.getCashTotal
import javax.inject.Inject

class CashSummaryViewModel @Inject constructor(val cashAdapter: CashAdapter) : ViewModel(), CashAdapter.Listener {
    val totalExpenses = ObservableField<Double>()
    private val totalBudget = ObservableField("0,00")
    private val totalSaving = ObservableField<Double?>()

    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"
    init {
        totalExpenses.set(0.0)
    }

    @Inject
    fun setListener(cashAdapter: CashAdapter) {
        cashAdapter.setLisener(this)
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

    fun setCashSummaryActivity(cashSummaryActivity: ActivityListener?) {
        cashAdapter.setActivityListener(cashSummaryActivity!!)
    }
}