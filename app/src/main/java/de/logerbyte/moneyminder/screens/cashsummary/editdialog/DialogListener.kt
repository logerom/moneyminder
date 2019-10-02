package de.logerbyte.moneyminder.screens.cashsummary.editdialog


interface DialogListener {
    fun editCash()
    fun editCashCancel()
}

interface DialogListenerView {
    fun dismissDialog()
}