package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogViewListener

class DeleteDialogViewModel(val expenseRepo: ExpenseRepo) : BaseDialogViewListener {

    override fun onClickOk() {
//        expenseRepo.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
//                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }
}
