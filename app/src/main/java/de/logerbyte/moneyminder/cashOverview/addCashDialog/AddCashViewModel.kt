package de.logerbyte.moneyminder.cashOverview.addCashDialog

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.base.ErrorHandling
import de.logerbyte.moneyminder.cashOverview.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.cashOverview.viewModels.CashViewModel
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.util.DigitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddCashViewModel(
        dialogCallback: DialogCallback,
        val context: Context?,
        val cashViewModel: CashViewModel,
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
        val cashInEuro = (DigitUtil.commaToDot(cashViewModel.cashViewItem.cashAmount.get())).toDouble()

        val expense = Expense(null, cashViewModel.cashViewItem.cashName.get(), cashViewModel.cashViewItem.cashCategory.get(),
                cashViewModel.cashViewItem.cashDate.get(), cashInEuro, cashViewModel.cashViewItem.cashPerson.get())

        expenseRepo
                .insertCashItemIntoDB(expense)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listCallback.onAddClicked()
                })
    }

    private fun isInputCorrect(): Boolean {
        return cashViewModel.cashViewItem.isAllSet()
    }
}