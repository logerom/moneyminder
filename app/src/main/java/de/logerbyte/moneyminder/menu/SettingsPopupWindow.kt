package de.logerbyte.moneyminder.menu

import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.data.SharedPrefManager
import de.logerbyte.moneyminder.menu.filter.FilterDialog
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter
import javax.inject.Inject

// fixme-SW: view reference from fragmentManger?
class SettingsPopupWindow @Inject constructor(
        val fragmentManager: FragmentManager,
        val menuVm: MenuVm,
        val sharedPrefManager: SharedPrefManager,
        val cashAdapter: CashAdapter)
    : MenuItemsClicked, PopupWindow.OnDismissListener {

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
        menuVm.budget.get()?.apply {
            sharedPrefManager.writeSharedPrefInt(SHARED_PREF_MENU_BUDGET, this)
        }
        cashAdapter.onBudgetUpdated()
    }
}

interface MenuItemsClicked {
    fun onFilterClicked(view: View)
    fun setDismissListener(listener: PopupWindow.OnDismissListener)
}
