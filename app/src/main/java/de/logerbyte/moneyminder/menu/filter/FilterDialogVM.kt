package de.logerbyte.moneyminder.menu.filter

import androidx.annotation.MainThread
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class FilterDialogVM @Inject constructor(val expenseRepo: ExpenseRepo) : ViewModel() {

    val categoriess = MutableLiveData<List<FilterDialogItem>>()

    init {
        expenseRepo.categories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categories -> initCategories(categories) })
    }

    private fun initCategories(categories: List<String>) {
        categoriess.value = categories.map { s: String -> FilterDialogItem(s, false) }
    }
}