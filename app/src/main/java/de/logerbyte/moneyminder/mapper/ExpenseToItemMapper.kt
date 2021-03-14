package de.logerbyte.moneyminder.mapper

import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import java.util.ArrayList
import javax.inject.Inject

class ExpenseToItemMapper @Inject constructor(): BaseMapper<List<Expense>, List<CashViewItem>> {

    // TODO: 13.03.21 create mapper lets mapp and push to view
    override fun map(from: List<Expense>): List<CashViewItem> {
        val cashViewItems = ArrayList<CashViewItem>()
        for (expense in from) {
            // TODO: 22.02.21 convert to kotlin and use data classes
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
}