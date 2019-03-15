package de.logerbyte.moneyminder.cashsummary.cashadapter

class ItemSummaryViewModel(cost: String) {
    companion object {
        val weekLimit = 70
    }

    var cost = cost
    var saldo = (weekLimit - cost.toDouble()).toString()
    // TODO: 15.03.19 saving = first cashout (in all) until last cashout in week
    // 1. which count of week ( ? x weekLimit = sum)
    // 2. sum - all cashOuts from first cashOut until last cashOut in actual week
    var saving = saldo
}