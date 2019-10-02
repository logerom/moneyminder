package de.logerbyte.moneyminder.screens.cashsummary.editdialog

import android.view.View


interface DialogListener {
    fun onClickOk(view: View)
    fun onClickCancel(view: View)
}

interface DialogListenerView {
    fun onClickCancel(view: View)
    fun onClickOk(view: View)
}