package de.logerbyte.moneyminder.dialogs.AddCashDialog

import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialog
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.viewModels.CashViewModel


class AddCashDialog : BaseDialog() {


    // todo-stewo: 2019-11-26 add cash layout + viewbinding

    lateinit var adapterCallback: AdapterCallBack
    private lateinit var addCashViewModel: AddCashViewModel
    private val cashViewModel = CashViewModel()

    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return addCashViewModel
    }

    override fun createContentBinding() {
        addCashViewModel = AddCashViewModel(this, context, cashViewModel, adapterCallback)
        val cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = cashViewModel }
        binding.dialogContainer.addView(cashView)
    }


}