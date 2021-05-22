package de.logerbyte.moneyminder.presentation.custom.settingsPopupWindow

import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.framework.SharedPrefManager
import javax.inject.Inject


class SettingsPopupViewModel @Inject constructor(sharedPrefManager: SharedPrefManager) {
    var budget = ObservableField<Int>()

    fun budget(value: String) {
        budget.set(value.toInt())
    }

    init {
        budget.set(sharedPrefManager.getSharedPrefInt(SHARED_PREF_MENU_BUDGET))
    }
}