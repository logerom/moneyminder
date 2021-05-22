package de.logerbyte.moneyminder.cashOverview.menu

import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.presentation.dialog.dialogFilter.FilterDialog
import de.logerbyte.moneyminder.framework.SharedPrefManager
import de.logerbyte.moneyminder.presentation.custom.settingsPopupWindow.SettingsPopupViewModel
import javax.inject.Inject

// fixme-SW: view reference from fragmentManger?
class SettingsPopupWindow @Inject constructor(
    val fragmentManager: FragmentManager,
    val settingsPopupViewModel: SettingsPopupViewModel,
    val sharedPrefManager: SharedPrefManager
)
    : MenuItemsClicked, PopupWindow.OnDismissListener {

    // TODO: 14.03.21 enherit from widget dont wrapp
    lateinit var popupWindow: PopupWindow

    fun createPopupWindow(contentView: View, with: Int, height: Int, focusable: Boolean): SettingsPopupWindow {
        popupWindow = PopupWindow(contentView, with, height, focusable).let {
            it.setOnDismissListener(this)
            it
        }
        return this
    }

    override fun onFilterClicked(view: View) {
        FilterDialog().show(fragmentManager, SettingsPopupWindow::class.simpleName)
    }

    override fun setDismissListener(listener: PopupWindow.OnDismissListener) {
        popupWindow.setOnDismissListener(listener)
    }

    override fun onDismiss() {
//        todo X: Update after setting
        writeBudgetInSharedPref()
//        cashAdapter.onBudgetUpdated()
    }

    private fun writeBudgetInSharedPref() {
        settingsPopupViewModel.budget.get()?.apply {
            sharedPrefManager.writeSharedPrefInt(SHARED_PREF_MENU_BUDGET, this)
        }
    }
}

interface MenuItemsClicked {
    fun onFilterClicked(view: View)
    fun setDismissListener(listener: PopupWindow.OnDismissListener)
}
