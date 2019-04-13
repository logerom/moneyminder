package de.logerbyte.moneyminder.cashsummary.cashadapter

import de.logerbyte.moneyminder.util.DigitUtil
import java.lang.Double.parseDouble
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ExpenseManager {

    var sdf = SimpleDateFormat("dd.MM.yy")
    val actualCalendar: Calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }

    companion object {
        val expenseLimit = 70
    }


    fun createWeeksAndDaysExpense(sortedExpenses: ArrayList<DayExpenseViewModel>): ArrayList<WeekSummaryViewModel> {

        var again = true
        var firstDate: Date?
        var firstWeek: Int? = null
        var expenseListNaturalOrder = ArrayList<WeekSummaryViewModel>()
        val datesToDelete = ArrayList<DayExpenseViewModel>()
        var subExpenseDays = ArrayList<DayExpenseViewModel>()
        var addedExpenseLimitEoverhead = 0.00

        actualCalendar.clear()

        while (again) {

            for (expense: DayExpenseViewModel in sortedExpenses) {
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
                    val expensesOfWeek = sumExpensesOfWeek(subExpenseDays)
                    val weekSaldo = expenseLimit - expensesOfWeek
                    addedExpenseLimitEoverhead = addedExpenseLimitEoverhead + expenseLimit - expensesOfWeek
                    val summaryWeek = WeekSummaryViewModel(expensesOfWeek, weekSaldo, addedExpenseLimitEoverhead,
                            subExpenseDays)
                    expenseListNaturalOrder.add(summaryWeek)
                    break
                }
            }
            sortedExpenses.removeAll(datesToDelete)
            subExpenseDays = ArrayList()

            if (sortedExpenses.isEmpty()) {
                again = false
            }
        }
        return descendWeekExpenses(expenseListNaturalOrder)
    }

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

    private fun descendWeekExpenses(expenseListNaturalOrder: ArrayList<WeekSummaryViewModel>): ArrayList<WeekSummaryViewModel> {
        val expenseDescend = ArrayList<WeekSummaryViewModel>()
        for (i in expenseListNaturalOrder.indices.reversed()) {
            expenseDescend.add(expenseListNaturalOrder[i])
        }
        return expenseDescend
    }

}