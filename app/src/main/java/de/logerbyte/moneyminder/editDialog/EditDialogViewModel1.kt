package de.logerbyte.moneyminder.editDialog

import android.content.Context
import android.view.View
import de.logerbyte.moneyminder.data.db.AppDatabaseManager
import de.logerbyte.moneyminder.data.db.expense.Expense
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.util.DigitUtil
import de.logerbyte.moneyminder.viewModels.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Double

class EditDialogViewModel1(
        val appDatabaseManager: AppDatabaseManager,
        dialogCallback: DialogCallback,
        val context: Context?,
        val cashViewModel: CashViewModel,
        val editDialogCallback: EditDialogCallback) : BaseDialogViewModel1(dialogCallback) {

    private lateinit var adapterCallBack: AdapterCallBack

    init {
        loadCategories()
    }

    private fun loadCategories() {
        appDatabaseManager.categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categoryList -> editDialogCallback.initCategories(categoryList as ArrayList<String>) }
    }

    override fun onClickOk(view: View) {
        val expenseToUpdate = Expense(cashViewModel.entryId,
                cashViewModel.cashName.get(), cashViewModel.cashCategory.get(),
                cashViewModel.cashDate.get(),
                Double.valueOf(DigitUtil.commaToDot(cashViewModel.cashAmount.get())))

        appDatabaseManager.updateCashItem(expenseToUpdate)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ aBoolean -> adapterCallBack.onUpdateItem() })
        super.onClickOk(view)
    }

    fun setAdapter(adapterCallback: AdapterCallBack) {
        adapterCallBack = adapterCallback
    }
}
