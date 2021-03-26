package de.logerbyte.moneyminder.data.viewItem

import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.BuildConfig

data class DayExpenseViewItem(var cashDate: String = "", var cashName: String = "", var cashAmount: String = "", var cashCategory: String = "",
                              var cashPerson: String = ""): ExpenseListViewItem, Parcelable {

    val cashPersonField=ObservableField<String>()
    val cashCategoryField=ObservableField<String>()
    val cashAmountField=ObservableField<String>()
    val cashNameField=ObservableField<String>()
    val cashDateField = ObservableField<String>()

    init {
        cashDateField.set(if(BuildConfig.DEBUG && cashDate.isBlank()) "11.11.11" else cashDate)
        cashNameField.set(if(BuildConfig.DEBUG && cashName.isBlank()) "Burger" else cashName)
        cashAmountField.set(if(BuildConfig.DEBUG && cashAmount.isBlank()) "1,11" else cashAmount)
        cashCategoryField.set(if(BuildConfig.DEBUG && cashCategory.isBlank()) "essen" else cashCategory)
        cashPersonField.set(if(BuildConfig.DEBUG && cashPerson.isBlank()) "1" else cashPerson)
    }

    fun isAllSet(): Boolean {
        return !(isSomeElementNull(listOf(cashDate, cashName, cashCategory, cashAmount, cashPerson)))
    }

    private fun isSomeElementNull(listOf: List<String?>): Boolean {
        for (element in listOf) {
            if (element.isNullOrEmpty()) {
                return true
            }
        }
        return false
    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeString(cashDate)
            writeString(cashName)
            writeString(cashAmount)
            writeString(cashCategory)
            writeString(cashPerson)
        }
    }

    companion object CREATOR : Parcelable.Creator<DayExpenseViewItem> {
        override fun createFromParcel(parcel: Parcel): DayExpenseViewItem {
            return DayExpenseViewItem(parcel)
        }

        override fun newArray(size: Int): Array<DayExpenseViewItem?> {
            return arrayOfNulls(size)
        }
    }
}