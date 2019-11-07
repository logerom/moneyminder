package de.logerbyte.moneyminder.dialogs


interface BaseDialog1ViewModelListener {
    fun onClickOk()
    fun onClickCancel()
}

interface BaseDialogViewListener {
    fun dismissDialog()
}