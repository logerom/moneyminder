package de.logerbyte.moneyminder.cashsummary.cashadapter

class WeekSummaryViewModel(costInWeek: Double, weekSaldo: Double, addedExpenseLimitEoverhead: Double) {

    var cost = costInWeek.toString()
    var saldo = weekSaldo.toString()
    var saving = addedExpenseLimitEoverhead.toString()
}