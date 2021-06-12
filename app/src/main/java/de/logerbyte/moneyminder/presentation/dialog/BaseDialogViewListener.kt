package de.logerbyte.moneyminder.presentation.dialog

import android.view.View


interface BaseDialogViewListener {
    fun onClickOk(view: View)
    fun onClickCancel(view: View)
}
