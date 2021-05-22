package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.mapper.ExpenseMapper
import de.logerbyte.moneyminder.presentation.dialog.CashViewModel
import javax.inject.Inject


class AddCashViewModel @Inject constructor(
    expenseRepo: ExpenseRepo,
    expenseMapper: ExpenseMapper)
    : CashViewModel(expenseRepo, expenseMapper)