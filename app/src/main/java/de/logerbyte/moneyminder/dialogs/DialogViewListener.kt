package de.logerbyte.moneyminder.dialogs

import android.view.View


interface DialogViewListener {
    fun onClickOk(view: View)
    fun onClickCancel(view: View)
}

interface DialogCallback {
    fun dismissDialog()
}