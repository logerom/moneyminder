package de.logerbyte.moneyminder.dialogs

import android.arch.lifecycle.ViewModel

abstract class BaseDialogViewModel1(
        private val dialogViewListener: BaseDialog1ViewListener)
    : ViewModel(), BaseDialog1ViewModelListener {
    override fun onClickOk() {
        dialogViewListener.dismissDialog()
    }

    override fun onClickCancel() {
        dialogViewListener.dismissDialog()
    }

}
