package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import android.app.Dialog
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment
import javax.inject.Inject

class DeleteDialogFragment : BaseDialogFragment<Parcelable>() {
    private lateinit var adapterCallback: AdapterCallBack
    var cashItemId: Long = 0L
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override fun viewContent(bundle: Bundle?): View {
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


