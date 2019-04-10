package de.logerbyte.moneyminder.cashsummary.cashadapter

class WeekSummaryViewModel(costInWeek: Double, weekSaldo: Double, addedExpenseLimitEoverhead: Double) {
    val cost = String.format("%.2f", costInWeek)
    val saldo = String.format("%.2f", weekSaldo)
    val saving = String.format("%.2f", addedExpenseLimitEoverhead)
}