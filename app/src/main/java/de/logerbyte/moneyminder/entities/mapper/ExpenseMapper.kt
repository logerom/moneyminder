package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewItem
import de.logerbyte.moneyminder.framework.database.ExpenseEntity
import de.logerbyte.moneyminder.entities.util.DigitUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(): BaseMapper<DayExpenseViewItem, ExpenseEntity> {
    override fun map(from: DayExpenseViewItem): ExpenseEntity {
        return ExpenseEntity(null, from.cashName, from.cashCategory, from.cashDate, DigitUtil.commaToDot(from.cashAmount).toDouble(), from.cashPerson)
    }

}
