package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.domain.mapper.ExpenseMapper
import de.logerbyte.moneyminder.presentation.dialog.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AddCashViewModel @Inject constructor(
    expenseRepo: ExpenseRepo,
    expenseMapper: ExpenseMapper)
    : CashViewModel(expenseRepo, expenseMapper)