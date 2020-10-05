package de.logerbyte.moneyminder.menu.filter

import android.view.View

interface FilterDialogVMListener {
    fun onClickCheckBox(view: View, checkBoxName: String)
}