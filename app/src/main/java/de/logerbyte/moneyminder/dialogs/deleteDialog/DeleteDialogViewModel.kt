package de.logerbyte.moneyminder.dialogs.deleteDialog

import android.content.Context
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteDialogViewModel(val adapterCallback: ViewInterface, val itemIdToDelete: Long, dialogCallback: DialogCallback, val context: Context?) : BaseDialogViewModel1(dialogCallback) {
    override fun onClickOk() {
        super.onClickOk()
        val appDatabaseManager = AppDatabaseManager(context)
        appDatabaseManager.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }
}
