package de.logerbyte.moneyminder.dialogs.editDialog


interface BseDialogListenerViewModel {
    fun editCash()
    fun editCashCancel()
}

interface BaseDialogListenerView {
    fun dismissDialog()
}