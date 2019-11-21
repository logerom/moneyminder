package de.logerbyte.moneyminder.viewModels

import android.text.Editable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.dialogs.DialogCallback
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel

class CashViewModel() : ViewModel() {
    // TODO: 2019-10-01 cash categories as string resources
    val cashCategoryList = ArrayList(listOf("Essen", "Sonstiges", "Beauty"))
    var entryId: Long = 0
    val cashDate = ObservableField<String>()
    val cashName = ObservableField<String>()
    lateinit var cashCategory: String
    var cashAmount = ObservableField<String>()

    constructor(dialogCallback: DialogCallback) : this()

    fun setCash(item: DayExpenseViewModel) {
        this.entryId = item.entryId
        this.cashDate.set(item.cashDate.get())
        this.cashName.set(item.cashName.get())
        this.cashAmount.set(item.cashInEuro.get())
        val cashCategory = item.cashCategory.get().orEmpty()
        this.cashCategory = cashCategory
        cashCategorySelectedItem.set(cashCategoryList.indexOf(cashCategory))
    }

    var newText = ""
    var dotDelete = false

    var cashCategorySelectedItem = ObservableInt()
        get() {
            if (field.get() != -1) cashCategory = cashCategoryList[field.get()]
            return field
        }

    fun onTextChanged(s: Editable) {
        if (dotDelete) {
            dotDelete = false
            return
        }
        // fixme: 02.09.18 make util class with that dot delete logic

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

