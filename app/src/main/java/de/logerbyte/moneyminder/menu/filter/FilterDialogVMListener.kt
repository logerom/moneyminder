package de.logerbyte.moneyminder.menu.filter

import android.widget.CheckBox

interface FilterDialogVMListener {
    fun onClickCheckBox(checkBox: CheckBox, checkBoxName: String)
}