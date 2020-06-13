package de.logerbyte.moneyminder.util

import de.logerbyte.moneyminder.db.expense.Expense
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

    fun getCashTotal(cashList: List<Expense>): String {
        var totalCash = 0.0
        for (expense in cashList) {
            totalCash = totalCash + expense.cashInEuro
        }
        return doubleToString(totalCash)
    }

    fun <T> doubleToString(value: T): String {
        return String.format(Locale.getDefault(), "%.2f", value)
    }
}