package de.logerbyte.moneyminder.dialogs.editDialog

import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialog1
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewModelListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface
import de.logerbyte.moneyminder.viewModels.CashViewModel


class EditDialog : BaseDialog1() {

    lateinit var adapterCallback: ViewInterface
    lateinit var cash: DayExpenseViewModel
    val cashViewModel = CashViewModel()
    lateinit var editDialogViewModel1: EditDialogViewModel1

    override fun additionalBinding() {
        editDialogViewModel1 = EditDialogViewModel1(this, context, cashViewModel)
        val cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView).let { it!!.viewModel = cashViewModel }
        binding.dialogContainer.addView(cashView)
        cashViewModel.setCash(cash)
        editDialogViewModel1.setAdapter(adapterCallback)
    }


    override fun setViewModelListener(): BaseDialog1ViewModelListener {
        return editDialogViewModel1
    }
}