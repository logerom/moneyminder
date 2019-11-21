package de.logerbyte.moneyminder.dialogs.editDialog

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialog
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface
import de.logerbyte.moneyminder.viewModels.CashViewModel


class EditDialog : BaseDialog() {

    lateinit var adapterCallback: ViewInterface
    lateinit var cash: DayExpenseViewModel
    val cashViewModel = CashViewModel()
    lateinit var editDialogViewModel1: EditDialogViewModel1

    // TODO: 2019-11-07 use same for add cash + set name of button generally
    override fun createContentBinding() {
        editDialogViewModel1 = EditDialogViewModel1(this, context, cashViewModel)
        val cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView).let { it!!.viewModel = cashViewModel }
        binding.dialogContainer.addView(cashView)
        cashViewModel.setCash(cash)
        editDialogViewModel1.setAdapter(adapterCallback)
    }


    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return editDialogViewModel1
        // TODO: 2019-11-19 create baseDialogViewAction (for cancel and ok)
    }
}