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
        val sdf: SimpleDateFormat
) : BaseMapper<List<ExpenseEntity>, List<ExpenseListViewItem>> {

    /**
     * Adds a summary line after all expenses in month
     */
    override fun map(from: List<ExpenseEntity>): List<ExpenseListViewItem> {
        val viewItemList = ArrayList<ExpenseListViewItem>()
        var cashInMonth = 0.0

        // Todo x: Calculate portion expense and argue portion text to CashViewItem

        for (expenseIndex in from.indices) {
            val expense = from[expenseIndex]
            val localDate = parseLocalDate(expense)
            val portion = calculatePortion(expense.cashInEuro, expense.person)

            if(expenseIndex == 0)
                viewItemList.add(setCashItem(expense,portion.toString()))

            if (hasNext(expenseIndex, from)) {
                val expense1 = from[expenseIndex + 1]
                val nextLocalDate = parseLocalDate(expense1)
                val portionNext = calculatePortion(expense1.cashInEuro, expense1.person)

                if (isExpenseInSameMonth(localDate, nextLocalDate)) {
                    viewItemList.add(setCashItem(expense1, portionNext.toString()))
                    cashInMonth += portionNext
                } else {
                    viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth, BUDGET))
                    viewItemList.add(setCashItem(expense1, portionNext.toString()))
                    cashInMonth = 0.0
                }
            } else {
                viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth, BUDGET))
                cashInMonth = 0.0
            }
        }
        return viewItemList
    }

    private fun setCashItem(expense1: ExpenseEntity, portion: String) =
        CashViewItem(
            expense1.id ?: 0,
            expense1.cashDate,
            expense1.cashName,
            expense1.cashInEuro.toString(),
            expense1.category,
            portion
        )

    private fun calculatePortion(holeCash: Double, person: Int): Double {
        return try {
            holeCash.div(person)
        } catch (e: ArithmeticException) {
            return 0.00
        }
    }


    private fun isExpenseInSameMonth(
        localDate: LocalDate,
        nextLocalDate: LocalDate
    ) = localDate.month == nextLocalDate.month

    private fun parseLocalDate(expense: ExpenseEntity) =
        LocalDate.parse(expense.cashDate, DateTimeFormatter.ofPattern(DATE_PATTERN))

    private fun hasNext(expenseIndex: Int, from: List<ExpenseEntity>) = expenseIndex + 1 < from.size
}
