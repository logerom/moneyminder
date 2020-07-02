package de.logerbyte.moneyminder.menu

import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import de.logerbyte.moneyminder.menu.filter.FilterDialog
import javax.inject.Inject

// TODO-SW: view reference from fragmentManger?
class SettingsPopupWindow @Inject constructor(val fragmentManager: FragmentManager) : MenuItemsClicked, PopupWindow.OnDismissListener {
    lateinit var popupWindow: PopupWindow

    fun createPopupWindow(contentView: View, with: Int, height: Int, focusable: Boolean): SettingsPopupWindow {
        popupWindow = PopupWindow(contentView, with, height, focusable)
        return this
    }

    override fun onFilterClicked(view: View) {
        FilterDialog().show(fragmentManager, SettingsPopupWindow::class.simpleName)
    }

    override fun setDismissListener(listener: PopupWindow.OnDismissListener) {
        popupWindow.setOnDismissListener(listener)
    }

    override fun onDismiss() {
        // TODO-SW: get old onDissmiss from activity
    }
}

interface MenuItemsClicked {
    fun onFilterClicked(view: View)
    fun setDismissListener(listener: PopupWindow.OnDismissListener)
}
