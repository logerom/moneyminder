package de.logerbyte.moneyminder.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewItem
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.mapper.ExpenseMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class CashViewModel(
    val expenseRepo: ExpenseRepo,
    val expenseMapper: ExpenseMapper) : ViewModel() {

    var cashViewItem = DayExpenseViewItem()
    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList

    init {
        loadViewCategories()
    }

    private fun loadViewCategories() {
        expenseRepo.categories
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { _categoryList.value = it}
    }

    fun saveCash(dayExpenseViewItem: DayExpenseViewItem) {
        expenseRepo
                .insertCashItemIntoDB(expenseMapper.map(dayExpenseViewItem))
                .subscribeOn(Schedulers.io())
                .subscribe()
    }
}
