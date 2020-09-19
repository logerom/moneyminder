package de.logerbyte.moneyminder.menu.filter

import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.base.viewmodel.BaseViewModel
import de.logerbyte.moneyminder.data.SharedPrefManager
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilterDialogVM @Inject constructor(val expenseRepo: ExpenseRepo, val sharedPrefManager:
SharedPrefManager) : BaseViewModel(), FilterDialogVMListener {

    val rawCategoriess = MutableLiveData<List<FilterDialogItem>>()
    val checkedSet = hashSetOf<String>()

    init {
        initFilterCategories()
    }

    override fun onClickCheckBox(checkBox: CheckBox, checkBoxName: String) {
        if (checkBox.isChecked) checkedSet.add(checkBoxName) else checkedSet.remove(checkBoxName)
    }

    private fun initFilterCategories() {
        // TODO-SW: read checked state. data-source sharedPref, when null, check db
        loadRawCategories()
    }

    private fun loadRawCategories() {
        expenseRepo.categories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categories -> initRawCategories(categories) }
                .addTo(compositeDisposable)
    }

    private fun initRawCategories(categories: List<String>) {
        rawCategoriess.value = categories.map { s: String -> FilterDialogItem(s, false) }
    }
}