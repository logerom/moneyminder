package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.framework.database.Expense
import de.logerbyte.moneyminder.entities.util.DigitUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(): BaseMapper<CashViewItem, Expense> {
    override fun map(from: CashViewItem): Expense {
        return Expense(null, from.cashName, from.cashCategory, from.cashDate, DigitUtil.commaToDot(from.cashAmount).toDouble(), from.cashPerson)
    }

}
