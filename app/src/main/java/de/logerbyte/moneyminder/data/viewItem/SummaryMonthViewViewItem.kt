package de.logerbyte.moneyminder.data.viewItem

import java.util.*

class SummaryMonthViewViewItem(val cost: Double,
                               val budget: Double,
                               val saving: Double,
                               val dayExpense: ArrayList<CashViewViewItem>): ExpenseListViewItem