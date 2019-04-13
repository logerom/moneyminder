package de.logerbyte.moneyminder.cashsummary.cashadapter

import java.util.*

class WeekSummaryViewModel(costInWeek: Double, weekSaldo: Double, addedExpenseLimitEoverhead: Double, dayExpenseViewModel: ArrayList<DayExpenseViewModel>) {

    val cost = String.format("%.2f", costInWeek)
    val saldo = String.format("%.2f", weekSaldo)
    val saving = String.format("%.2f", addedExpenseLimitEoverhead)
    val dayExpense = dayExpenseViewModel

}