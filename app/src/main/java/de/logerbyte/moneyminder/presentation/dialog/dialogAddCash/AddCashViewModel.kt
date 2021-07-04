package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.logerbyte.moneyminder.domain.Verification
import de.logerbyte.moneyminder.domain.textOrBlank
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.domain.database.ExpenseRepo
import de.logerbyte.moneyminder.entities.mapper.ExpenseMapper
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogViewListener
import de.logerbyte.moneyminder.presentation.dialog.CashViewModel
import javax.inject.Inject


class AddCashViewModel @Inject constructor(
    expenseRepo: ExpenseRepo,
    expenseMapper: ExpenseMapper,
    val verification: Verification) : CashViewModel(expenseRepo, expenseMapper), BaseDialogViewListener {

    private val _isInputCorrect = MutableLiveData<Boolean>()
    val isInputCorrect :LiveData<Boolean> = _isInputCorrect

    override fun onClickOk() {
        if(isInputCorrect(cashViewItem)) {
            _isInputCorrect.value = true
            saveCash(cashViewItem)
        } else
            _isInputCorrect.value = false
    }

    private fun isInputCorrect(cashViewItem: CashViewItem) =
        verification.isDateTextCorrect(cashViewItem.cashDateField.get().textOrBlank())

    override fun onClickCancel() {
        TODO("Not yet implemented")
    }
}
