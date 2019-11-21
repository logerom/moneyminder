package de.logerbyte.moneyminder.dialogs.deleteDialog

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.dialogs.BaseDialog
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.BUNDLE_CASHITEM_ID
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface

class DeleteDialog : BaseDialog() {
    private lateinit var adapterCallback: ViewInterface
    var cashItemId: Long = 0L
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        cashItemId = arguments!!.getLong(BUNDLE_CASHITEM_ID)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun createContentBinding() {
        deleteDialogViewModel = DeleteDialogViewModel(adapterCallback, cashItemId, this, context)
        val question = TextView(context).apply {
            setText(R.string.question_delete)
        }
        binding.dialogContainer.addView(question)
    }

    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return deleteDialogViewModel
    }

    fun setAdapterCallback(adapterCallBack: ViewInterface) {
        this.adapterCallback = adapterCallBack
    }
}


