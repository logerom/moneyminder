package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.domain.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.entities.mapper.map
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogViewListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DeleteDialogViewModel(val expenseRepo: ExpenseRepo) : BaseDialogViewListener {
    lateinit var vmData: CashViewItem
    private val _isDeleted = MutableLiveData<Boolean>()
    val isDeleted: LiveData<Boolean> = _isDeleted

    override fun onClickOk() {
        expenseRepo.deleteCashItem(vmData.map())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { aBoolean -> _isDeleted.value = aBoolean }
    }

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }

}
