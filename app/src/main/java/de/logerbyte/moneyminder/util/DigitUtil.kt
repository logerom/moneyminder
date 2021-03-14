package de.logerbyte.moneyminder.util

import androidx.databinding.InverseMethod
import de.logerbyte.moneyminder.domain.database.expense.Expense
import java.util.*

/**
 * Created by logerom on 04.08.18.
 */
object DigitUtil {
    fun commaToDot(commaString: String?): String {
        return commaString?.replace(",", ".") ?: ""
    }

    fun dotToComma(commaString: String): String {
        return commaString.replace(".", ",")
    }

    fun getCashTotal(cashList: List<Expense>): Double {
        var totalCash = 0.0
        for (expense in cashList) {
            totalCash = totalCash + expense.cashInEuro
        }
        return totalCash
    }

    @JvmStatic
    fun doubleToString(value: Double): String {
        return String.format(Locale.getDefault(), "%.2f", value)
    }

    @JvmStatic
    @InverseMethod("stringToInt")
    fun IntToString(value: Int): String {
        return value.toString()
    }

    @JvmStatic
    fun stringToInt(value: String) = if (value == "") 0 else value.toInt()
}