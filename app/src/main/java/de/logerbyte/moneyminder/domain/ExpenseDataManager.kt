package de.logerbyte.moneyminder.domain

import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.domain.util.DigitUtil
import de.logerbyte.moneyminder.data.viewItem.WeekSummaryViewItem
import io.reactivex.schedulers.Schedulers
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class ExpenseDataManager @Inject constructor(val sharedPrefManager: SharedPrefManager,
                                             val expenseRepo: ExpenseRepo) {

    private var weeksAndDays = ArrayList<WeekSummaryViewItem>()
    var budget: Int = 0

    var sdf = SimpleDateFormat("dd.MM.yy", Locale.getDefault())

    val actualCalendar: Calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }
    var firstDate: Date? = null
    var firstWeek: Int? = null
    // TODO: sort expenses in week

    init {
        loadBudgetFromSharedPref()
    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    fun loadExpenseList() = expenseRepo.allExpense.subscribeOn(Schedulers.io())




    fun createWeeksAndDaysExpense(sortedExpenses: ArrayList<DayExpenseViewModel>): ArrayList<WeekSummaryViewItem> {
        weeksAndDays.clear()
        val sortedExpensesCopy = sortedExpenses.toMutableList()
        val datesToDelete = ArrayList<DayExpenseViewModel>()
        var subExpenseDays = ArrayList<DayExpenseViewModel>()
        var again = true
        actualCalendar.clear()

        while (again) {

            for (expense: DayExpenseViewModel in sortedExpensesCopy) {
                // starting week/expense which is use to compare with other expenses
                if (sortedExpensesCopy.indexOf(expense) == 0) {
                    firstDate = getDateFromViewModel(expense)
                    actualCalendar.time = firstDate
                    firstWeek = actualCalendar.get(Calendar.WEEK_OF_YEAR)
                }
                // date and week to compare with actual date
                val dateToCompare = getDateFromViewModel(expense)
                this.actualCalendar.time = dateToCompare
                val weekToCompare = this.actualCalendar.get(Calendar.WEEK_OF_YEAR)

                if (weekToCompare == firstWeek) {
                    subExpenseDays.add(expense)
                    datesToDelete.add(expense)
                } else {
                    // no more expense days in this week -> next week + summary line
                    addExpenseToSummaryWeek(subExpenseDays, weeksAndDays)
                    break
                }
            }

            sortedExpensesCopy.removeAll(datesToDelete)

            if (sortedExpensesCopy.isEmpty()) {
                // is list empty add last remaining expense to week
                if (subExpenseDays.isNotEmpty()) {
                    addExpenseToSummaryWeek(subExpenseDays, weeksAndDays)
                }
                again = false
            }

            subExpenseDays = ArrayList()

        }
        return descendWeekExpenses(weeksAndDays)
    }

    fun loadBudgetFromSharedPref() {
        budget = sharedPrefManager.getSharedPrefInt(SHARED_PREF_MENU_BUDGET)
    }

    private fun addExpenseToSummaryWeek(subExpenseDays: ArrayList<DayExpenseViewModel>, expenseListNaturalOrder: ArrayList<WeekSummaryViewItem>) {
        val expensesOfWeek = sumExpensesOfWeek(subExpenseDays)
        val expenseDiff = budget - expensesOfWeek
        val summaryWeek = WeekSummaryViewItem(expensesOfWeek, budget.toDouble(), expenseDiff,
                subExpenseDays)
        expenseListNaturalOrder.add(summaryWeek)
    }

    // TODO: up to DateTimeUtil
    private fun getDateFromViewModel(itemViewModel: DayExpenseViewModel): Date? {
        val dateString = itemViewModel.cashDate.get()
        return sdf.parse(dateString)
    }

    private fun sumExpensesOfWeek(week: ArrayList<DayExpenseViewModel>): Double {
        var cashSummary = 0.0
        for (vm in week) {
            cashSummary += parseDouble(DigitUtil.commaToDot(vm.cashInEuro.get()))
        }
        return cashSummary
    }

    private fun descendWeekExpenses(expenseListNaturalOrder: ArrayList<WeekSummaryViewItem>): ArrayList<WeekSummaryViewItem> {
        val expenseDescend = ArrayList<WeekSummaryViewItem>()
        for (i in expenseListNaturalOrder.indices.reversed()) {
            expenseDescend.add(expenseListNaturalOrder[i])
        }
        return expenseDescend
    }

    fun getOverAllBudget(): Int {
        return weeksAndDays.size * budget
    }
}