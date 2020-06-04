package de.logerbyte.moneyminder.addCashDialog

import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.base.ErrorHandling
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.db.expense.Expense
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.util.DigitUtil
import de.logerbyte.moneyminder.viewModels.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddCashViewModel(
        dialogCallback: DialogCallback,
        val context: Context?,
        val cashViewModel: CashViewModel,
        val listCallback: AdapterCallBack,
        val appDatabaseManager: AppDatabaseManager
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
        val cashInEuro = (DigitUtil.commaToDot(cashViewModel.cashAmount.get())).toDouble()

        val expense = Expense(null, cashViewModel.cashName.get(), cashViewModel.cashCategory,
                cashViewModel.cashDate.get(), cashInEuro)

        appDatabaseManager
                .insertCashItemIntoDB(expense)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listCallback.onAddClicked()
                })
    }

    private fun isInputCorrect(): Boolean {
        return cashViewModel.isAllSet()
    }
}