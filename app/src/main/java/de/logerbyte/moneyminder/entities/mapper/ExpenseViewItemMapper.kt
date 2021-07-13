package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.BUDGET
import de.logerbyte.moneyminder.DATE_PATTERN
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.data.viewData.ExpenseListViewItem
import de.logerbyte.moneyminder.entities.data.viewData.SummaryMonthViewItem
import de.logerbyte.moneyminder.domain.database.ExpenseEntity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpenseViewItemMapper @Inject constructor(
        val sdf: SimpleDateFormat) : BaseMapper<List<ExpenseEntity>, List<ExpenseListViewItem>> {

    companion object { const val firstExpense = 0 }

    /**
     * Adds a summary line after all expenses in month
     */
    override fun map(from: List<ExpenseEntity>): List<ExpenseListViewItem> {
        val viewItemList = ArrayList<ExpenseListViewItem>()
        var totalCashback = 0.00
        var totalExpense = 0.00

        for (expenseIndex in from.indices) {
            val expense = from[expenseIndex]
            val expenseId = expense.id
            val expenseLocaleDate = parseLocalDate(expense)
            val expenseDate = expense.cashDate
            val expenseName = expense.cashName
            val expenseCategory = expense.category
            val expenseCash = expense.cashInEuro
            val expensePerson = expense.person
            val expenseCashback = calculateCashback(expenseCash, expensePerson)

            if(expenseIndex == firstExpense) {
                viewItemList.add(
                    CashViewItem(expenseId?:0, expenseDate, expenseName, expenseCash.toString(), expenseCategory, expensePerson.toString(), expenseCashback.toString()))
                totalExpense += expense.cashInEuro
                totalCashback += expenseCashback
            }

            if (isNextExpense(expenseIndex, from)) {
                val nextExpense = from[expenseIndex + 1]
                val nextExpenseId = nextExpense.id
                val nextExpenseLocaleDate = parseLocalDate(nextExpense)
                val nextExpenseDate = nextExpense.cashDate
                val nextExpenseName = nextExpense.cashName
                val nextExpenseCategory = nextExpense.category
                val nextExpenseCash = nextExpense.cashInEuro
                val nextExpensePerson = nextExpense.person
                val nextExpenseCashback = calculateCashback(nextExpense.cashInEuro, nextExpense.person)

                if (isNextExpenseInSameMonth(expenseLocaleDate, nextExpenseLocaleDate)) {
                    viewItemList.add(
                        CashViewItem(nextExpenseId?:0, nextExpenseDate, nextExpenseName, nextExpenseCash.toString(), nextExpenseCategory, nextExpensePerson.toString(), nextExpenseCashback.toString()))
                    totalExpense += nextExpenseCash
                    totalCashback += nextExpenseCashback
                } else {
                    viewItemList.add(
                        SummaryMonthViewItem(totalExpense,BUDGET - totalExpense, BUDGET,totalCashback))
                    viewItemList.add(
                        CashViewItem(nextExpenseId?:0, nextExpenseDate, nextExpenseName, nextExpenseCash.toString(), nextExpenseCategory, nextExpensePerson.toString(), nextExpenseCashback.toString()))
                    totalExpense = nextExpenseCash
                    totalCashback = nextExpenseCashback
                }
            } else {
                viewItemList.add(
                    SummaryMonthViewItem(totalExpense,BUDGET - totalExpense, BUDGET,totalCashback))
                totalExpense = 0.00
                totalCashback = 0.00
            }
        }
        return viewItemList
    }

    private fun calculateCashback(holeCash: Double, person: Int): Double {
        return try {
            val cashForOnePerson: Double = holeCash.div(person)
            holeCash - cashForOnePerson
        } catch (e: ArithmeticException) {
            return holeCash
        }
    }

    private fun isNextExpenseInSameMonth(
        localDate: LocalDate,
        nextLocalDate: LocalDate
    ) = localDate.month == nextLocalDate.month

    private fun parseLocalDate(expense: ExpenseEntity) =
        LocalDate.parse(expense.cashDate, DateTimeFormatter.ofPattern(DATE_PATTERN))

    private fun isNextExpense(expenseIndex: Int, from: List<ExpenseEntity>) = expenseIndex + 1 < from.size
}
