package de.logerbyte.moneyminder.dialogs


interface BaseDialog1ViewModelListener {
    fun onClickOk()
    fun onClickCancel()
}

interface BaseDialog1ViewListener {
    fun dismissDialog()
}