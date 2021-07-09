package de.logerbyte.moneyminder.entities.mapper

import android.text.format.DateUtils
import de.logerbyte.moneyminder.domain.database.ExpenseEntity
import de.logerbyte.moneyminder.domain.textOrBlank
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewViewItem
import de.logerbyte.moneyminder.entities.util.DigitUtil

fun CashViewItem.map()
    = ExpenseEntity(cashId, cashNameField.get().textOrBlank(), cashCategoryField.get().textOrBlank(),
                    cashDateField.get().textOrBlank(), cashAmountField.get().textOrBlank().toDouble(),
                    cashPersonField.get().textOrBlank())
