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


class AddCashViewModel(
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


    var changedQueryText: String? = ""

    var submittedQueryText = MutableLiveData<String>()

    override fun onClickOk(view: View) {
        if (isInputCorrect()) {
            saveCashAndReloadList()
        } else
            ErrorHandling.showToast(view.context, "Input should not be null")
        // todo-stewo: 2019-12-04 reload adapter
        super.onClickOk(view)
    }

    private fun saveCashAndReloadList() {
        val cashInEuro = (DigitUtil.commaToDot(editDialogViewModel.cashViewItem.cashAmount)).toDouble()

        val expense = Expense(null, editDialogViewModel.cashViewItem.cashName, editDialogViewModel.cashViewItem.cashCategory,
                editDialogViewModel.cashViewItem.cashDate, cashInEuro, editDialogViewModel.cashViewItem.cashPerson)

        expenseRepo
                .insertCashItemIntoDB(expense)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listCallback.onAddClicked()
                })
    }

    private fun isInputCorrect(): Boolean {
        return editDialogViewModel.cashViewItem.isAllSet()
    }
}