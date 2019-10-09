package de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1

import android.arch.lifecycle.ViewModel
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewModelListener

class BaseDialogViewModel1(
        private val dialogViewListener: BaseDialog1ViewListener)
    : ViewModel(), BaseDialog1ViewModelListener {
    override fun onClickOk() {
        dialogViewListener.dismissDialog()
    }

    override fun onClickCancel() {
        dialogViewListener.dismissDialog()
    }

}
