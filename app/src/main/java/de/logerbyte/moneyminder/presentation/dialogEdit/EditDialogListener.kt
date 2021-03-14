package de.logerbyte.moneyminder.presentation.dialogEdit


interface BseDialogListenerViewModel {
    fun editCash()
    fun editCashCancel()
}

interface BaseDialogListenerView {
    fun dismissDialog()
}