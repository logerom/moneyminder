package de.logerbyte.moneyminder.entities.data.database

import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.domain.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.data.viewData.SummaryMonthViewItem
import de.logerbyte.moneyminder.domain.SharedPrefManager
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class ExpenseDataManager @Inject constructor(val sharedPrefManager: SharedPrefManager,
                                             val expenseRepo: ExpenseRepo) {

    private var weeksAndDays = ArrayList<SummaryMonthViewItem>()
    var budget: Int = 0

    var sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    fun loadExpenseList() = expenseRepo.allExpense

    fun loadBudgetFromSharedPref() {
        budget = sharedPrefManager.getSharedPrefInt(SHARED_PREF_MENU_BUDGET)
    }

    fun getOverAllBudget(): Int {
        return weeksAndDays.size * budget
    }
}
