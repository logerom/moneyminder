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
        var cashInMonth = 0.00

        for (expenseIndex in from.indices) {
            val expenseAmount = from[expenseIndex]
            val expenseDate = parseLocalDate(expenseAmount)
            val expensePerPerson = calculatePortion(expenseAmount.cashInEuro, expenseAmount.person)

            if(expenseIndex == 0) {
                viewItemList.add(setCashItem(expenseAmount,expensePerPerson.toString()))
                cashInMonth += expensePerPerson
            }

            if (hasNext(expenseIndex, from)) {
                val nextExpenseAmount = from[expenseIndex + 1]
                val nextExpenseDate = parseLocalDate(nextExpenseAmount)
                val nextExpensePerPerson = calculatePortion(nextExpenseAmount.cashInEuro, nextExpenseAmount.person)

                if (isExpenseInSameMonth(expenseDate, nextExpenseDate)) {
                    viewItemList.add(setCashItem(nextExpenseAmount, nextExpensePerPerson.toString()))
                    cashInMonth += nextExpensePerPerson
                } else {
                    viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth, BUDGET))
                    cashInMonth = nextExpensePerPerson
                    viewItemList.add(setCashItem(nextExpenseAmount, nextExpensePerPerson.toString()))
                }
            } else {
                viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth, BUDGET))
                cashInMonth = 0.00
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
            expense1.person.toString(),
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
