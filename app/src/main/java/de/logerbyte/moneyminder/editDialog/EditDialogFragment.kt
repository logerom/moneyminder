package de.logerbyte.moneyminder.editDialog

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.db.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.viewModels.CashViewModel
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject


class EditDialogFragment : BaseDialogFragment(), EditDialogCallback {

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    @Inject
    lateinit var cashViewModel: CashViewModel

    lateinit var cashView: View
    lateinit var adapterCallback: AdapterCallBack
    lateinit var cash: DayExpenseViewModel
    lateinit var editDialogViewModel1: EditDialogViewModel1

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        editDialogViewModel1 = EditDialogViewModel1(expenseRepo, this, context, cashViewModel, this)
        cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = cashViewModel }
        viewBinding.dialogContainer.addView(cashView)
        cashViewModel.setCash(cash)
        editDialogViewModel1.setAdapter(adapterCallback)
    }


    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener {
        return editDialogViewModel1
        // TODO: 2019-11-19 create baseDialogViewAction (for cancel and ok)
    }

    override fun initCategories(categories: ArrayList<String>) {
        custom_searchlist.list = categories
    }
}