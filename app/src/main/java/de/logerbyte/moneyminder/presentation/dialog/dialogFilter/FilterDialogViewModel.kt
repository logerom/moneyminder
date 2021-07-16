package de.logerbyte.moneyminder.presentation.dialog.dialogFilter

import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.domain.base.viewmodel.BaseViewModel
import de.logerbyte.moneyminder.entities.data.viewData.FilterDialogViewItem
import de.logerbyte.moneyminder.domain.SharedPrefManager
import de.logerbyte.moneyminder.domain.database.ExpenseRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilterDialogViewModel @Inject constructor(val expenseRepo: ExpenseRepo, val sharedPrefManager:
SharedPrefManager
) : BaseViewModel(), FilterDialogVMListener {

    val rawCategories = MutableLiveData<List<FilterDialogViewItem>>()
    val checkedCategories = setOf<String>()

    init {
        initFilterCategories()
    }

    override fun onClickCheckBox(view: View, checkBoxName: String) {
        (view as CheckBox).apply {
            if (this.isChecked) checkedCategories.plus(checkBoxName) else checkedCategories.minus(checkBoxName)
        }
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
        rawCategories.value = categories.map { s: String -> FilterDialogViewItem(s, false) }
    }

    fun applyFilter() {

        // TODO-SW: 1. get expenses with checked categories.
        expenseRepo.expensesWithCategories(checkedCategories)

        // TODO-SW: ? when something alter in repo all observer should be noticed. So the recycler view updated
        // automatic
    }
}
