package de.logerbyte.moneyminder.cashsummary.cashadapter

import de.logerbyte.moneyminder.util.DigitUtil
import java.text.SimpleDateFormat
import java.util.*


class ExpenseManager {

    var sdf = SimpleDateFormat("dd.MM.yy")
    val actualCalendar: Calendar = Calendar.getInstance().apply { firstDayOfWeek = Calendar.MONDAY }

    companion object {
        val expenseLimit = 70
    }


    fun createWeeksAndDaysExpense(sortedExpenses: ArrayList<DayExpenseViewModel>): LinkedHashMap<WeekSummaryViewModel, ArrayList<DayExpenseViewModel>> {
        val map = LinkedHashMap<WeekSummaryViewModel, ArrayList<DayExpenseViewModel>>()
        var again = true
        var firstDate: Date?
        var firstWeek: Int? = null
        val datesToDelete = ArrayList<DayExpenseViewModel>()
        var subExpenseDays = ArrayList<DayExpenseViewModel>()
        var addedExpenseLimitEoverhead = 0.00

        actualCalendar.clear()
        map.clear()

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
                    // TODO: 10.04.19 round error = SummaryLine_B = SummaryLine_A + ExpensesInWeek
                    addedExpenseLimitEoverhead = DigitUtil.roundFloatingPoint(addedExpenseLimitEoverhead + DigitUtil.roundFloatingPoint
                    (expenseLimit - expensesOfWeek))
                    val summaryWeek = WeekSummaryViewModel(firstWeek!!, expensesOfWeek, weekSaldo,
                            addedExpenseLimitEoverhead)
                    map[summaryWeek] = subExpenseDays

                    break
                }
            }
            sortedExpenses.removeAll(datesToDelete)
            subExpenseDays = ArrayList()

            if (sortedExpenses.isEmpty()) {
                again = false
            }
        }
        return map
    }

    private fun getDateFromViewModel(itemViewModel: DayExpenseViewModel): Date? {
        val dateString = itemViewModel.cashDate.get()
        return sdf.parse(dateString)
    }

    private fun sumExpensesOfWeek(week: ArrayList<DayExpenseViewModel>): Double {
        var cashSummary = 0.0
        for (vm in week) {
            cashSummary += java.lang.Double.parseDouble(DigitUtil.commaToDot(vm.cashInEuro.get()))
        }
        return cashSummary
    }

}