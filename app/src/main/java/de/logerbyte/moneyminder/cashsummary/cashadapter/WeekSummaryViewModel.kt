package de.logerbyte.moneyminder.cashsummary.cashadapter

import java.util.*

class WeekSummaryViewModel(costInWeek: Double, weekSaldo: Double, addedExpenseLimitEoverhead: Double, firstDateInWeek: Date) {


    // TODO: 13.04.19 implement first date in week for comparing
    val date = firstDateInWeek
    val cost = String.format("%.2f", costInWeek)
    val saldo = String.format("%.2f", weekSaldo)
    val saving = String.format("%.2f", addedExpenseLimitEoverhead)
}