package de.logerbyte.moneyminder.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.domain.mapper.ExpenseMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class CashViewModel(
        val expenseRepo: ExpenseRepo,
        val expenseMapper: ExpenseMapper) : ViewModel() {

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
    fun saveCash(cashViewItem: CashViewItem) {
        expenseRepo.insertCashItemIntoDB(expenseMapper.map(cashViewItem))
    }

    var cashViewItem = CashViewItem()
}
