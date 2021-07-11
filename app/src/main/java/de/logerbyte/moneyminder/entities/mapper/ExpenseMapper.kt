package de.logerbyte.moneyminder.entities.mapper

import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.domain.database.ExpenseEntity
import de.logerbyte.moneyminder.domain.textOrBlank
import de.logerbyte.moneyminder.entities.util.DigitUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseMapper @Inject constructor(): BaseMapper<CashViewItem, ExpenseEntity> {
    override fun map(from: CashViewItem): ExpenseEntity {
        return ExpenseEntity(null, from.cashNameField.get().textOrBlank(), from.cashCategoryField.get().textOrBlank(),
            from.cashDateField.get().textOrBlank(), DigitUtil.commaToDot(from.cashAmountField.get()).toDouble(), from.cashPersonField.get()?.toInt()?:1)
    }
}
