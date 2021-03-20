package de.logerbyte.moneyminder.domain.mapper

import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.util.DigitUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(): BaseMapper<CashViewItem, Expense> {
    // todo X: needs to copy?
    override fun map(from: CashViewItem): Expense {
        return Expense(null, from.cashName, from.cashCategory, from.cashDate, DigitUtil.commaToDot(from.cashAmount).toDouble(), from.cashPerson)
    }

}