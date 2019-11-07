package de.logerbyte.moneyminder.dialogs.editDialog

import android.content.Context
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.db.expense.Expense
import de.logerbyte.moneyminder.dialogs.BaseDialogViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface
import de.logerbyte.moneyminder.util.DigitUtil
import de.logerbyte.moneyminder.viewModels.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Double

class EditDialogViewModel1(baseDialogViewListener: BaseDialogViewListener, val context: Context?, val cashViewModel: CashViewModel) : BaseDialogViewModel1
(baseDialogViewListener) {
    private lateinit var viewInterface: ViewInterface

    override fun onClickOk() {
        val expenseToUpdate = Expense(cashViewModel.entryId,
                cashViewModel.cashName.get(), cashViewModel.cashCategory,
                cashViewModel.cashDate.get(),
                Double.valueOf(DigitUtil.commaToDot(cashViewModel.cashAmount.get()))!!)

        val appDatabaseManager = AppDatabaseManager(context)
        appDatabaseManager.updateCashItem(expenseToUpdate).subscribeOn(
                Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                { aBoolean -> viewInterface.onUpdateItem() })
        super.onClickOk()
    }

    fun setAdapter(adapterCallback: ViewInterface) {
        viewInterface = adapterCallback
    }
}
