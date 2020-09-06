package de.logerbyte.moneyminder.menu.filter

import androidx.lifecycle.ViewModel
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import javax.inject.Inject

class FilterDialogVM @Inject constructor(val expenseRepo: ExpenseRepo) : ViewModel() {
    init {
        // TODO-SW: use use-cases and inject it
    }
}