package de.logerbyte.moneyminder.presentation.dialog.dialogEdit


interface BseDialogListenerViewModel {
    fun editCash()
    fun editCashCancel()
}

interface BaseDialogListenerView {
    fun dismissDialog()
}