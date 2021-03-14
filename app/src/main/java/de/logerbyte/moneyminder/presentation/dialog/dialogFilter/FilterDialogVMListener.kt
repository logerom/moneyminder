package de.logerbyte.moneyminder.presentation.dialog.dialogFilter

import android.view.View

interface FilterDialogVMListener {
    fun onClickCheckBox(view: View, checkBoxName: String)
}