package de.logerbyte.moneyminder.dialogs


interface DialogViewListener {
    fun onClickOk()
    fun onClickCancel()
}

interface DialogCallback {
    fun dismissDialog()
}