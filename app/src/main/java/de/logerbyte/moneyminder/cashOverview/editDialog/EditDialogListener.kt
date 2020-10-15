package de.logerbyte.moneyminder.cashOverview.editDialog


interface BseDialogListenerViewModel {
    fun editCash()
    fun editCashCancel()
}

interface BaseDialogListenerView {
    fun dismissDialog()
}