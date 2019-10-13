package de.logerbyte.moneyminder.dialogs.deleteDialog

import android.content.Context
import android.support.v7.widget.util.SortedListAdapterCallback
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteDialogViewModel(val adapterCallback: ViewInterface, val itemIdToDelete: Long, baseDialog1ViewListener: BaseDialog1ViewListener, val context: Context?): BaseDialogViewModel1(baseDialog1ViewListener) {
    override fun onClickOk() {
        super.onClickOk()
        val appDatabaseManager = AppDatabaseManager(context)
        appDatabaseManager.deleteCashItem(itemIdToDelete!!).subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe { aBoolean -> adapterCallback.onItemDeleted()}
    }
}
