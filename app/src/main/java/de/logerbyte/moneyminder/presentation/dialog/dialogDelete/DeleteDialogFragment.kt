package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogViewListener
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment
import javax.inject.Inject

class DeleteDialogFragment : BaseDialogFragment() {
    private lateinit var adapterCallback: AdapterCallBack
    var cashItemId: Long = 0L
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override val onOKClicked: () -> Unit
        get() = TODO("Not yet implemented")
    override val onCancelClicked: () -> Unit
        get() = TODO("Not yet implemented")

    override fun viewContent(): View {
        TODO("Not yet implemented")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        cashItemId = arguments!!.getLong("")
        return super.onCreateDialog(savedInstanceState)
    }

//    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
//        deleteDialogViewModel = DeleteDialogViewModel(expenseRepo, adapterCallback, cashItemId, this, context)
//        val question = TextView(context).apply {
//            setText(R.string.question_delete)
//        }
//        viewBinding.dialogContainer.addView(question)
//    }

    fun setAdapterCallback(adapterCallBack: AdapterCallBack) {
        this.adapterCallback = adapterCallBack
    }
}


