package de.logerbyte.moneyminder.dialogs.editDialog


interface DialogListener {
    fun editCash()
    fun editCashCancel()
}

interface DialogListenerView {
    fun dismissDialog()
}