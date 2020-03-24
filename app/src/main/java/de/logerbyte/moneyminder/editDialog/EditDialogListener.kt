package de.logerbyte.moneyminder.editDialog


interface BseDialogListenerViewModel {
    fun editCash()
    fun editCashCancel()
}

interface BaseDialogListenerView {
    fun dismissDialog()
}