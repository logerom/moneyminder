package de.logerbyte.moneyminder.domain.mapper

import de.logerbyte.moneyminder.data.viewItem.CashViewViewItem
import de.logerbyte.moneyminder.data.viewItem.ExpenseListViewItem
import de.logerbyte.moneyminder.data.viewItem.SummaryMonthViewViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class MonthSummaryItemViewMapper @Inject constructor(
    val sdf: SimpleDateFormat
): BaseMapper<List<Expense>, List<SummaryMonthViewViewItem>> {

    val actualCalendar: Calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }
    var firstDate: Date? = null
    var firstWeek: Int? = null
    private var weeksAndDays = ArrayList<SummaryMonthViewViewItem>()
    var budget: Int = 0

    /**
     * Epense list needs to be sorted in days
     */
    override fun map(from: List<Expense>): List<SummaryMonthViewViewItem> {
        val viewItemList = ArrayList<ExpenseListViewItem>()

        for (expenseIndex in from.indices) {

            val expense = from[expenseIndex]
            viewItemList.add(CashViewViewItem(expense.cashDate, expense.cashName, expense.cashInEuro.toString(), expense.category, expense.person))
            val expense1 = if(expenseIndex + 1 < from.size) from[expenseIndex + 1] else break

            val localDate = LocalDate.parse(expense.cashDate)
            val nextLocalDate = LocalDate.parse(expense1.cashDate)

            if(localDate.month == nextLocalDate.month){
                viewItemList.add(CashViewViewItem(expense1.cashDate, expense1.cashName, expense1.cashInEuro.toString(), expense1.category, expense1.person))
            }else{
                // TODO: 15.03.21 MonthSummaryLine with all expenses in month AND Savings = limit - expenses
            }
        }
        java.time.Clock.
//        return createWeeksAndDaysExpense(ArrayList(from))
        return summerMonthExpense(from)
    }

    private fun summerMonthExpense(from: List<Expense>): List<SummaryMonthViewViewItem> {
        TODO("Not yet implemented")
    }

    fun createWeeksAndDaysExpense(sortedExpenses: ArrayList<Expense>): List<SummaryMonthViewViewItem> {
        weeksAndDays.clear()
        val datesToDelete = ArrayList<Expense>()
        var subExpenseDays = ArrayList<Expense>()
        var again = true
        actualCalendar.clear()

        while (again) {

            for (expense: Expense in sortedExpenses) {
                // starting week/expense which is use to compare with other expenses
                if (sortedExpenses.indexOf(expense) == 0) {
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

            sortedExpenses.removeAll(datesToDelete)

            if (sortedExpenses.isEmpty()) {
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
    ArrayList<SummaryMonthViewViewItem>) {
        val expensesOfWeek = sumExpensesOfWeek(subExpenseDays)
        val expenseDiff = budget - expensesOfWeek
        val summaryWeek = SummaryMonthViewViewItem(expensesOfWeek, budget.toDouble(), expenseDiff,
            createCashViewItem(subExpenseDays))
        expenseListNaturalOrder.add(summaryWeek)
    }

    private fun createCashViewItem(expenses: java.util.ArrayList<Expense>): java.util.ArrayList<CashViewViewItem> {
        val cashViewItems = java.util.ArrayList<CashViewViewItem>()
        for (expense in expenses) {
            // todo X: copy operator? Are properties linked?
            val item = CashViewViewItem().apply {
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

    private fun descendWeekExpenses(expenseListNaturalOrder: ArrayList<SummaryMonthViewViewItem>): ArrayList<SummaryMonthViewViewItem> {
        val expenseDescend = ArrayList<SummaryMonthViewViewItem>()
        for (i in expenseListNaturalOrder.indices.reversed()) {
            expenseDescend.add(expenseListNaturalOrder[i])
        }
        return expenseDescend
    }

}