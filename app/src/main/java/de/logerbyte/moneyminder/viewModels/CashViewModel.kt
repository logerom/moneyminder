package de.logerbyte.moneyminder.viewModels

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import de.logerbyte.moneyminder.cashsummary.cashadapter.DayExpenseViewModel

class CashViewModel : ViewModel() {
    // TODO: 2019-10-01 cash categories as string resources
    val cashCategoryList = ArrayList(listOf("Essen", "Sonstiges", "Beauty"))
    var entryId: Long = 0
    val cashDate = ObservableField<String>()
    val cashName = ObservableField<String>()
    lateinit var cashCategory: String
    var cashAmount = ObservableField<String>()

    fun setCash(item: DayExpenseViewModel) {
        this.entryId = item.getEntryId()
        this.cashDate.set(item.getCashDate().get())
        this.cashName.set(item.getCashName().get())
        this.cashAmount.set(item.getCashInEuro().get())
        val cashCategory = item.getCashCategory().get().orEmpty()
        this.cashCategory = cashCategory
        cashCategorySelectedItem.set(cashCategoryList.indexOf(cashCategory))
    }

    var newText = ""
    var dotDelete = false

    var cashCategorySelectedItem = ObservableInt()
        get() {
            cashCategory = cashCategoryList[field.get()]
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

