package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.base.ErrorHandling
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogViewModel
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.domain.util.DigitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddCashViewModel(
    dialogCallback: DialogCallback,
    val context: Context?,
    val editDialogViewModel: EditDialogViewModel,
    val listCallback: AdapterCallBack,
    val expenseRepo: ExpenseRepo
) : BaseDialogViewModel1(dialogCallback) {

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
        val cashInEuro = (DigitUtil.commaToDot(editDialogViewModel.cashViewItem.cashAmount.get())).toDouble()

        val expense = Expense(null, editDialogViewModel.cashViewItem.cashName.get(), editDialogViewModel.cashViewItem.cashCategory.get(),
                editDialogViewModel.cashViewItem.cashDate.get(), cashInEuro, editDialogViewModel.cashViewItem.cashPerson.get())

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