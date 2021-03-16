package de.logerbyte.moneyminder.domain.mapper

import de.logerbyte.moneyminder.BUDGET
import de.logerbyte.moneyminder.data.viewItem.CashViewViewItem
import de.logerbyte.moneyminder.data.viewItem.ExpenseListViewItem
import de.logerbyte.moneyminder.data.viewItem.SummaryMonthViewViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.math.exp

class MonthSummaryItemViewMapper @Inject constructor(
    val sdf: SimpleDateFormat
): BaseMapper<List<Expense>, List<ExpenseListViewItem>> {

    /**
     * Epense list needs to be sorted in days
     */
    override fun map(from: List<Expense>): List<ExpenseListViewItem> {
        val viewItemList = ArrayList<ExpenseListViewItem>()
        var cashInMonth = 0.0

        for (expenseIndex in from.indices) {

            val expense = from[expenseIndex]
            val localDate = LocalDate.parse(expense.cashDate)
            val expense1 = from[expenseIndex+1]
            val nextLocalDate = LocalDate.parse(expense1.cashDate)

            viewItemList.add(CashViewViewItem(expense.cashDate, expense.cashName, expense.cashInEuro.toString(), expense.category, expense.person))
            cashInMonth += expense.cashInEuro

            if(localDate.month == nextLocalDate.month){
                viewItemList.add(CashViewViewItem(expense1.cashDate, expense1.cashName, expense1.cashInEuro.toString(), expense1.category, expense1.person))
                cashInMonth += expense1.cashInEuro
            }else{
                viewItemList.add(SummaryMonthViewViewItem(cashInMonth, BUDGET - cashInMonth))
                cashInMonth = 0.0
            }
        }
        return viewItemList
    }
}