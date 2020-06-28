package de.logerbyte.moneyminder.menu

import android.view.View
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

    fun onCLickFilter(view: View){
        // TODO-SW: 27.06.20 open filter dialog
    }
}