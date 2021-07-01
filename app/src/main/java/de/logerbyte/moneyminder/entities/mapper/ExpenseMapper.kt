package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.framework.database.ExpenseEntity
import de.logerbyte.moneyminder.entities.util.DigitUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(): BaseMapper<CashViewItem, ExpenseEntity> {
    override fun map(from: CashViewItem): ExpenseEntity {
        return ExpenseEntity(null, from.cashNameField.get(), from.cashCategoryField.get(), from.cashDateField.get(), DigitUtil.commaToDot(from.cashAmountField.get()).toDouble(), from.cashPersonField.get())
    }
}
