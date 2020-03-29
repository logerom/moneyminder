package de.logerbyte.moneyminder.deleteDialog

import android.content.Context
import android.view.View
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DeleteDialogViewModel(val adapterCallback: AdapterCallBack, val itemIdToDelete: Long, dialogCallback: DialogCallback, val context: Context?) : BaseDialogViewModel1(dialogCallback) {
    @Inject
    lateinit var appDatabaseManager: AppDatabaseManager

    override fun onClickOk(view: View) {
        super.onClickOk(view)
        appDatabaseManager.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted() }
    }
}
