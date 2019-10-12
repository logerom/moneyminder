package de.logerbyte.moneyminder.dialogs.editDialog

import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialog1
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewModelListener
import de.logerbyte.moneyminder.viewModels.CashViewModel


class EditDialog : BaseDialog1(), BaseDialog1ViewListener {

    private val cashViewModel = CashViewModel()
    private val editDialogViewModel1 = EditDialogViewModel1(this).apply {
        this.cashViewmodel = cashViewModel
    }

    override fun additionalBinding() {
        createBinding(R.layout.frame_cash).let {
            it as FrameCashBinding
            it.viewModel = cashViewModel
        }
    }


    override fun setViewModelListener(): BaseDialog1ViewModelListener {
        return editDialogViewModel1
    }
}