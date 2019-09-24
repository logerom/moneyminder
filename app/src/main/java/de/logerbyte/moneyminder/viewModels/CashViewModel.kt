package de.logerbyte.moneyminder.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable

class CashViewModel(application: Application) : AndroidViewModel(application) {
    val cashDate = ObservableField<String>()
    val cashName = ObservableField<String>()
    val cashCategory = ""
    val cashAmount = ObservableField<String>()

    var newText = ""
    var dotDelete = false

    val cashCategoryList = ArrayList<String>()

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

    init {
        cashCategoryList.add("Essen")
        cashCategoryList.add("Sonstiges")
        cashCategoryList.add("Beauty")
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

