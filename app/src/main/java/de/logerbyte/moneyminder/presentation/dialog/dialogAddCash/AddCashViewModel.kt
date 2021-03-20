package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.base.ErrorHandling
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogViewModel
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.domain.util.DigitUtil
import de.logerbyte.moneyminder.presentation.dialog.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class AddCashViewModel @Inject constructor(
    val expenseRepo: ExpenseRepo): CashViewModel() {

    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    init {
        loadViewCategories()
    }

    private fun loadViewCategories() {
        expenseRepo.categories
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { _categoryList.value = it}
    }

}