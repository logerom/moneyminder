package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogViewListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//class EditDialogViewModel(
//    val expenseRepo: ExpenseRepo,
//    dialogCallback: DialogCallback,
//    val context: Context?,
//    val editDialogCallback: EditDialogCallback) : BaseDialogViewModel1(dialogCallback) {

class EditDialogViewModel @Inject constructor(val expenseRepo: ExpenseRepo): ViewModel(), BaseDialogViewListener {

    private lateinit var adapterCallBack: AdapterCallBack
    private lateinit var data: CashViewItem
    val shallDialogClose = MutableLiveData<Boolean>()
    private val _shallDialogClose = shallDialogClose


    private var _categories = MutableLiveData<ArrayList<String>>()
    val categories: LiveData<ArrayList<String>> = _categories

    init {
        loadCategories()
    }

    fun setData(cashViewViewItem: CashViewItem) {
        this.data = cashViewViewItem
    }

    private fun loadCategories() {
        expenseRepo.categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categories -> _categories.value = categories as ArrayList }
    }

//    override fun onClickOk(view: View) {
//        val expenseToUpdate = Expense(editDialogViewModel.cashViewItem.entryId,
//                editDialogViewModel.cashViewItem.cashName.get(), editDialogViewModel.cashViewItem.cashCategory.get(),
//                editDialogViewModel.cashViewItem.cashDate.get(),
//                Double.valueOf(DigitUtil.commaToDot(editDialogViewModel.cashViewItem.cashAmount.get())),
//                editDialogViewModel.cashViewItem.cashPerson.get()
//            )
//
//        expenseRepo.updateCashItem(expenseToUpdate)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ aBoolean -> adapterCallBack.onUpdateItem() })
//        super.onClickOk(view)
//    }

    fun setAdapter(adapterCallback: AdapterCallBack) {
        adapterCallBack = adapterCallback
    }

    override fun onClickOk() {
        _shallDialogClose.value = true
    }

    override fun onClickCancel() {
        _shallDialogClose.value = true    }
}
