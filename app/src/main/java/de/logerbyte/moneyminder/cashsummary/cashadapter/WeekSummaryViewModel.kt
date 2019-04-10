package de.logerbyte.moneyminder.cashsummary.cashadapter

class WeekSummaryViewModel(costInWeek: Double, weekSaldo: Double, addedExpenseLimitEoverhead: Double) {
    val cost = costInWeek.toString()
    val saldo = weekSaldo.toString()
    val saving = addedExpenseLimitEoverhead.toString()
}