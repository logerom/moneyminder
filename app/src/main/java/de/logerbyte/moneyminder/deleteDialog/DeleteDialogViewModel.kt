package de.logerbyte.moneyminder.deleteDialog

import android.content.Context
import android.view.View
import de.logerbyte.moneyminder.data.db.AppDatabaseManager
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteDialogViewModel(val appDatabaseManager: AppDatabaseManager, val adapterCallback: AdapterCallBack, val itemIdToDelete: Long, dialogCallback: DialogCallback, val context: Context?) : BaseDialogViewModel1(dialogCallback) {

    override fun onClickOk(view: View) {
        super.onClickOk(view)
        appDatabaseManager.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }
}
