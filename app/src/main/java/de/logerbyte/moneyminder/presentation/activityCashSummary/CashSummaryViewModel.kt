package de.logerbyte.moneyminder.presentation.activityCashSummary

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import de.logerbyte.moneyminder.entities.data.viewData.ExpenseListViewItem
import de.logerbyte.moneyminder.entities.data.database.ExpenseDataManager
import de.logerbyte.moneyminder.framework.database.ExpenseEntity
import de.logerbyte.moneyminder.entities.mapper.ExpenseViewItemMapper
import de.logerbyte.moneyminder.entities.util.DigitUtil.dotToComma
import de.logerbyte.moneyminder.entities.util.DigitUtil.getCashTotal
import java.text.SimpleDateFormat
import javax.inject.Inject

class CashSummaryViewModel @Inject constructor(val expenseDataManager: ExpenseDataManager,
                                               val viewItemMapper: ExpenseViewItemMapper,
                                               val sdf: SimpleDateFormat) : ViewModel() {
    val totalExpenses = ObservableField<Double>()
    private val totalBudget = ObservableField("0,00")
    private val totalSaving = ObservableField<Double?>()
    private val _cashItems = MutableLiveData<List<ExpenseListViewItem>>()
    val cashItems: LiveData<List<ExpenseListViewItem>> = _cashItems

    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"
    init {
        totalExpenses.set(0.0)
    }

    fun observeExpenses() =
        expenseDataManager.loadExpenseList()
            .map { it -> it.sortedBy { sdf.parse(it.cashDate) } }
            .map { viewItemMapper.map(it) }

    //    todo sw: Need this functions?
    private fun addCashToTotal(cashList: List<ExpenseEntity>) {
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

//    override fun onLoadedExpenses(expenses: List<Expense>, allBudget: Int) {
//        addCashToTotal(expenses)
//        totalBudget.set(doubleToString(allBudget.toDouble()))
//        totalSaving.set(java.lang.Double.valueOf(allBudget.toDouble()) - totalExpenses.get()!!)
//    }
}
