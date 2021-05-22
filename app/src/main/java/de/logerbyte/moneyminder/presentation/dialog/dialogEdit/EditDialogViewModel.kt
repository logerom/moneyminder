package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//class EditDialogViewModel(
//    val expenseRepo: ExpenseRepo,
//    dialogCallback: DialogCallback,
//    val context: Context?,
//    val editDialogCallback: EditDialogCallback) : BaseDialogViewModel1(dialogCallback) {

class EditDialogViewModel @Inject constructor(val expenseRepo: ExpenseRepo): ViewModel() {

    private lateinit var adapterCallBack: AdapterCallBack
    private lateinit var data: DayExpenseViewItem

    private var _categories = MutableLiveData<ArrayList<String>>()
    val categories: LiveData<ArrayList<String>> = _categories

    init {
        loadCategories()
    }

    fun setData(dayExpenseViewViewItem: DayExpenseViewItem) {
        this.data = dayExpenseViewViewItem
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
}
