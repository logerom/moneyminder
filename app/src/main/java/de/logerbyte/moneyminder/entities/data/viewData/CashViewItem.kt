package de.logerbyte.moneyminder.entities.data.viewData

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.BuildConfig
import de.logerbyte.moneyminder.entities.FeatureFlag
import de.logerbyte.moneyminder.entities.util.DigitUtil

data class CashViewItem(
    val cashId: Long = 0,
    private val cashDate: String = "",
    private val cashName: String = "",
    private val cashAmount: String = "",
    private val cashCategory: String = "",
    private val cashPerson: String = "",
    val cashback: String = ""
): ExpenseListViewItem, Parcelable {

    // Todo x: Add Cashback field in MonthSummaryLine and overall summaryLine

    var cashPersonField=ObservableField<String>()
    val cashCategoryField=ObservableField<String>()
    val cashAmountField=ObservableField<String>()
    val cashNameField=ObservableField<String>()
    val cashDateField = ObservableField<String>()

    init {
        cashDateField.set(if(FeatureFlag.CASH_ITEM_FILLED && cashDate.isBlank()) "11.11.11" else cashDate)
        cashNameField.set(if(FeatureFlag.CASH_ITEM_FILLED && cashName.isBlank()) "Burger" else cashName)
        cashAmountField.set(if(FeatureFlag.CASH_ITEM_FILLED && cashAmount.isBlank()) "1,11" else cashAmount)
        cashCategoryField.set(if(FeatureFlag.CASH_ITEM_FILLED && cashCategory.isBlank()) "essen" else cashCategory)
        cashPersonField.set(if(FeatureFlag.CASH_ITEM_FILLED && cashPerson.isBlank()) "1" else cashPerson)
    }

    private fun calculatePortion(): String {
        val amount: Double = cashAmountField.get()?.let { DigitUtil.commaToDot(it).toDouble() } ?: 0.0
        val portion: Double = try {
            amount.div(cashPersonField.get()?.toInt() ?: 1)
        } catch (e: ArithmeticException) {
            return "0.00"
        }
        return "$portion ($amount)"
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
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.apply {
            writeLong(cashId)
            writeString(cashDateField.get())
            writeString(cashNameField.get())
            writeString(cashAmountField.get())
            writeString(cashCategoryField.get())
            writeString(cashPersonField.get())
        }
    }

    companion object CREATOR : Parcelable.Creator<CashViewItem> {
        override fun createFromParcel(parcel: Parcel): CashViewItem {
            return CashViewItem(parcel)
        }

        override fun newArray(size: Int): Array<CashViewItem?> {
            return arrayOfNulls(size)
        }
    }
}
