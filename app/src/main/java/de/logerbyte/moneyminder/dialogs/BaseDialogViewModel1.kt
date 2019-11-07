package de.logerbyte.moneyminder.dialogs

import androidx.lifecycle.ViewModel

abstract class BaseDialogViewModel1(
        private val dialogViewListener: BaseDialogViewListener)
    : ViewModel(), BaseDialog1ViewModelListener {
    override fun onClickOk() {
        dialogViewListener.dismissDialog()
    }

    override fun onClickCancel() {
        dialogViewListener.dismissDialog()
    }

}
