package de.logerbyte.moneyminder.domain

import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.data.viewItem.SummaryMonthViewViewItem
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class ExpenseDataManager @Inject constructor(val sharedPrefManager: SharedPrefManager,
                                             val expenseRepo: ExpenseRepo) {

    private var weeksAndDays = ArrayList<SummaryMonthViewViewItem>()
    var budget: Int = 0

    var sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())


    // TODO: sort expenses in week


    fun loadExpenseList() = expenseRepo.allExpense.subscribeOn(Schedulers.io())

    fun loadBudgetFromSharedPref() {
        budget = sharedPrefManager.getSharedPrefInt(SHARED_PREF_MENU_BUDGET)
    }

    fun getOverAllBudget(): Int {
        return weeksAndDays.size * budget
    }
}