package de.logerbyte.moneyminder.presentation.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.DATE_PATTERN
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.mapper.ExpenseMapper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

open class CashViewModel(
    val expenseRepo: ExpenseRepo,
    val expenseMapper: ExpenseMapper) : ViewModel() {

    var cashViewItem = CashViewItem()
    private val _categoryList = MutableLiveData<List<String>>()
    val categoryList: LiveData<List<String>> = _categoryList
    private var _isSaved  = MutableLiveData<Boolean>()
    var isSaved: LiveData<Boolean> = _isSaved

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
        expenseRepo
            .insertCashItemIntoDB(expenseMapper.map(cashViewItem))
            .subscribeOn(Schedulers.io())
            .subscribe(Consumer { _isSaved.postValue(true)})
    }
}
