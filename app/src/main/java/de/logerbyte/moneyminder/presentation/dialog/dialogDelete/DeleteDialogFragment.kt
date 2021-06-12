package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.DialogDeleteBinding
import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewItem
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.framework.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.BaseFragment
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragmentv1
import javax.inject.Inject

class DeleteDialogFragment: BaseDialogFragmentv1(), BaseFragment {
    private lateinit var adapterCallback: AdapterCallBack
    var cashItemId: Long = 0L
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override fun viewContent(bundle: Bundle?): View {
        val binding = DataBindingUtil.inflate<DialogDeleteBinding>(layoutInflater, R.layout.dialog_delete, null, false)
        val data = getParcel<DayExpenseViewItem>()

        return binding.root
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


