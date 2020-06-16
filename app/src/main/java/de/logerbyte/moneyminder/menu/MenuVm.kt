package de.logerbyte.moneyminder.menu

import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.data.SharedPrefManager
import javax.inject.Inject


class MenuVm @Inject constructor(private val sharedPrefManager: SharedPrefManager) {
    var text = ObservableField<String>()

    init {
        text.set(sharedPrefManager.getSharedPrefString(SHARED_PREF_MENU_BUDGET))
    }
}