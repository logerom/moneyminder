package de.logerbyte.moneyminder.dialogs

import android.view.View
import androidx.lifecycle.ViewModel

abstract class BaseDialogViewModel1(private val dialogCallback: DialogCallback) : ViewModel(), BaseDialogActionListener {

    override fun onClickOk(view: View) {
        dialogCallback.dismissDialog()
    }

    override fun onClickCancel(view: View) {
        dialogCallback.dismissDialog()
    }

}
