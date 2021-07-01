package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.BUDGET
import de.logerbyte.moneyminder.DATE_PATTERN
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.data.viewData.ExpenseListViewItem
import de.logerbyte.moneyminder.entities.data.viewData.SummaryMonthViewItem
import de.logerbyte.moneyminder.framework.database.ExpenseEntity
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ExpenseViewItemMapper @Inject constructor(
        val sdf: SimpleDateFormat
) : BaseMapper<List<ExpenseEntity>, List<ExpenseListViewItem>> {

    /**
     * Epense list needs to be sorted in days
     */
    override fun map(from: List<ExpenseEntity>): List<ExpenseListViewItem> {
        val viewItemList = ArrayList<ExpenseListViewItem>()
        var cashInMonth = 0.0

        for (expenseIndex in from.indices) {
            val hasNextItem = expenseIndex + 1 < from.size
            val expense = from[expenseIndex]
            val localDate = LocalDate.parse(expense.cashDate, DateTimeFormatter.ofPattern(DATE_PATTERN))

            viewItemList.add(CashViewItem(expense.cashDate?:"", expense.cashName?:"", expense.cashInEuro.toString(), expense.category?:"", expense.person?:""))
            cashInMonth += expense.cashInEuro

            if (hasNextItem) {
                val expense1 = from[expenseIndex + 1]
                val nextLocalDate = LocalDate.parse(expense1.cashDate, DateTimeFormatter.ofPattern(DATE_PATTERN))

                if (localDate.month == nextLocalDate.month) {
                    viewItemList.add(CashViewItem(expense1.cashDate?:"", expense1.cashName?:"", expense1.cashInEuro.toString(), expense1.category?:"", expense1.person?:""))
                    cashInMonth += expense1.cashInEuro
                } else {
                    viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth))
                    cashInMonth = 0.0
                }
            } else {
                viewItemList.add(SummaryMonthViewItem(cashInMonth, BUDGET - cashInMonth))
                cashInMonth = 0.0
            }
        }
        return viewItemList
    }
}
