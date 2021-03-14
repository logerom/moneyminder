package de.logerbyte.moneyminder.cashOverview.deleteDialog

import android.content.Context
import android.view.View
import de.logerbyte.moneyminder.cashOverview.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteDialogViewModel(val expenseRepo: ExpenseRepo, val adapterCallback: AdapterCallBack, val itemIdToDelete: Long, dialogCallback: DialogCallback, val context: Context?) : BaseDialogViewModel1(dialogCallback) {

    override fun onClickOk(view: View) {
        super.onClickOk(view)
        expenseRepo.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }
}
