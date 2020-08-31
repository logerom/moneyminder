package de.logerbyte.moneyminder.deleteDialog

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.BUNDLE_CASHITEM_ID
import javax.inject.Inject

class DeleteDialogFragment : BaseDialogFragment() {
    private lateinit var adapterCallback: AdapterCallBack
    var cashItemId: Long = 0L
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        cashItemId = arguments!!.getLong(BUNDLE_CASHITEM_ID)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        deleteDialogViewModel = DeleteDialogViewModel(expenseRepo, adapterCallback, cashItemId, this, context)
        val question = TextView(context).apply {
            setText(R.string.question_delete)
        }
        viewBinding.dialogContainer.addView(question)
    }

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener {
        return deleteDialogViewModel
    }

    fun setAdapterCallback(adapterCallBack: AdapterCallBack) {
        this.adapterCallback = adapterCallBack
    }
}


