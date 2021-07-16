package de.logerbyte.moneyminder.presentation.dialog.dialogDelete

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.DialogDeleteBinding
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.ExpenseRepo
import de.logerbyte.moneyminder.presentation.BaseFragment
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragmentv1
import javax.inject.Inject

class DeleteDialogFragment: BaseDialogFragmentv1(), BaseFragment {

    @Inject
    lateinit var deleteDialogViewModel: DeleteDialogViewModel

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    override fun viewContent(bundle: Bundle?): View {
        val binding = DataBindingUtil.inflate<DialogDeleteBinding>(layoutInflater, R.layout.dialog_delete, null, false)
        deleteDialogViewModel.vmData = getParcel<CashViewItem>()?:CashViewItem()
        baseDialogBinding.viewListener = deleteDialogViewModel
        initLiveData()
        return binding.root
    }

    private fun initLiveData() {
        deleteDialogViewModel.isDeleted.observe(viewLifecycleOwner, Observer {
            if(it) dismiss()
        })
    }
}


