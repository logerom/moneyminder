package de.logerbyte.moneyminder.cashOverview.menu

import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.data.SharedPrefManager
import javax.inject.Inject


class MenuVm @Inject constructor(sharedPrefManager: SharedPrefManager) {
    var budget = ObservableField<Int>()

    fun budget(value: String) {
        budget.set(value.toInt())
    }

    init {
        budget.set(sharedPrefManager.getSharedPrefInt(SHARED_PREF_MENU_BUDGET))
    }
}