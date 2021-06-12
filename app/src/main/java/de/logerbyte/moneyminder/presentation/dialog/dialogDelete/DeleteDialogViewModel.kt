package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import android.view.View
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogViewListener

class DeleteDialogViewModel(val expenseRepo: ExpenseRepo) : BaseDialogViewListener {

    override fun onClickOk(view: View) {
//        expenseRepo.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
//                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }

    override fun onClickCancel(view: View) {
        TODO("Not yet implemented")
    }
}
