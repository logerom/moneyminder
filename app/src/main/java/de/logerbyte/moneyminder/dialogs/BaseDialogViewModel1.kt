package de.logerbyte.moneyminder.dialogs

import androidx.lifecycle.ViewModel

abstract class BaseDialogViewModel1(
        private val dialogCallback: DialogCallback)
    : ViewModel(), DialogViewListener {
    override fun onClickOk() {
        dialogCallback.dismissDialog()
    }

    override fun onClickCancel() {
        dialogCallback.dismissDialog()
    }

}
