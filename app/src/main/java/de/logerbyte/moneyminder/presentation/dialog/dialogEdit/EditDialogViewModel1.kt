package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import android.content.Context
import android.view.View
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.expense.Expense
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.domain.util.DigitUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Double

class EditDialogViewModel1(
    val expenseRepo: ExpenseRepo,
    dialogCallback: DialogCallback,
    val context: Context?,
    val editDialogViewModel: EditDialogViewModel,
    val editDialogCallback: EditDialogCallback) : BaseDialogViewModel1(dialogCallback) {

    private lateinit var adapterCallBack: AdapterCallBack

    init {
        loadCategories()
    }

    private fun loadCategories() {
        expenseRepo.categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categoryList -> editDialogCallback.initCategories(categoryList as ArrayList<String>) }
    }

    override fun onClickOk(view: View) {
//        val expenseToUpdate = Expense(editDialogViewModel.cashViewItem.entryId,
//                editDialogViewModel.cashViewItem.cashName.get(), editDialogViewModel.cashViewItem.cashCategory.get(),
//                editDialogViewModel.cashViewItem.cashDate.get(),
//                Double.valueOf(DigitUtil.commaToDot(editDialogViewModel.cashViewItem.cashAmount.get())),
//                editDialogViewModel.cashViewItem.cashPerson.get()
//            )

//        expenseRepo.updateCashItem(expenseToUpdate)
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ aBoolean -> adapterCallBack.onUpdateItem() })
        super.onClickOk(view)
    }

    fun setAdapter(adapterCallback: AdapterCallBack) {
        adapterCallBack = adapterCallback
    }
}
