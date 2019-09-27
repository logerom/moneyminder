package de.logerbyte.moneyminder.viewModels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import de.logerbyte.moneyminder.cashsummary.cashadapter.DayExpenseViewModel

class CashViewModel : ViewModel() {
    val cashCategoryList = ArrayList<String>()
    var entryId: Long = 0
    val cashDate = ObservableField<String>()
    val cashName = ObservableField<String>()
    var cashCategory = ""
    val cashAmount = ObservableField<String>()


    fun setCash(item: DayExpenseViewModel) {
        this.entryId = item.getEntryId()
        this.cashDate.set(item.getCashDate().get())
        this.cashName.set(item.getCashName().get())
        this.cashAmount.set(item.getCashInEuro().get())
        this.cashCategory = item.getCashCategory().get().orEmpty()
    }

    init {
        cashCategoryList.add("Essen")
        cashCategoryList.add("Sonstiges")
        cashCategoryList.add("Beauty")
    }

    var newText = ""

    var dotDelete = false

    private var isNewSpinnerValue = false
    private var newSpinnerIndex: Int = 0
    private var newSCategoryName: String? = null
    val cashCategorySelectedItem: ObservableInt
        get() {
            if (isNewSpinnerValue) {
                newSpinnerIndex = cashCategorySelectedItem.get()
                newSCategoryName = cashCategoryList[newSpinnerIndex]
                isNewSpinnerValue = false
            } else {
                isNewSpinnerValue = true
            }
            return cashCategorySelectedItem

        }

    fun onTextChanged(s: Editable) {
        if (dotDelete) {
            dotDelete = false
            return
        }
        // todo: 02.09.18 make util class with that dot delete logic

        if (s.length < newText.length) {
            val charAt = newText.toString().get(newText.toString().length - 1)
            if (charAt == '.') {
                dotDelete = true
                s.delete(s.toString().length - 1, s.toString().length)
            }
            newText = s.toString()
            return
        }

        if (s.toString().length == 2 || s.toString().length == 5) {
            s.append(".")
            newText = s.toString()
            return
        }

        newText = s.toString()
    }
}

