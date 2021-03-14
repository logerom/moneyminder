package de.logerbyte.moneyminder.domain.mapper

import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.data.viewItem.WeekSummaryViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.util.DigitUtil
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class WeekSummaryItemMapper @Inject constructor(
    val sdf: SimpleDateFormat
): BaseMapper<List<Expense>, List<WeekSummaryViewItem>> {

    val actualCalendar: Calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }
    var firstDate: Date? = null
    var firstWeek: Int? = null
    private var weeksAndDays = ArrayList<WeekSummaryViewItem>()
    var budget: Int = 0


    override fun map(from: List<Expense>): List<WeekSummaryViewItem> {
        return createWeeksAndDaysExpense(from)
    }

    fun createWeeksAndDaysExpense(sortedExpenses: List<Expense>): List<WeekSummaryViewItem> {
        weeksAndDays.clear()
        val sortedExpensesCopy = sortedExpenses.toMutableList()
        val datesToDelete = ArrayList<Expense>()
        var subExpenseDays = ArrayList<Expense>()
        var again = true
        actualCalendar.clear()

        while (again) {

            for (expense: Expense in sortedExpensesCopy) {
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

    private fun addExpenseToSummaryWeek(subExpenseDays: ArrayList<Expense>, expenseListNaturalOrder:
    ArrayList<WeekSummaryViewItem>) {
        val expensesOfWeek = sumExpensesOfWeek(subExpenseDays)
        val expenseDiff = budget - expensesOfWeek
        val summaryWeek = WeekSummaryViewItem(expensesOfWeek, budget.toDouble(), expenseDiff,
            createCashViewItem(subExpenseDays))
        expenseListNaturalOrder.add(summaryWeek)
    }

    private fun createCashViewItem(expenses: java.util.ArrayList<Expense>): java.util.ArrayList<CashViewItem> {
        val cashViewItems = java.util.ArrayList<CashViewItem>()
        for (expense in expenses) {
            // todo X: copy operator? Are properties linked?
            val item = CashViewItem().apply {
                cashDate.set(expense.cashDate)
                cashName.set(expense.cashName)
                cashAmount.set(expense.cashName)
                cashCategory.set(expense.category)
                cashPerson.set(expense.person)
            }
            cashViewItems.add(item)
        }
        return cashViewItems
    }

    // TODO: up to DateTimeUtil
    private fun getDateFromViewModel(expense: Expense): Date? {
        val dateString = expense.cashDate
        return sdf.parse(dateString)
    }

    private fun sumExpensesOfWeek(week: ArrayList<Expense>): Double {
        var cashSummary = 0.0
        for (vm in week) {
            cashSummary += vm.cashInEuro
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

}