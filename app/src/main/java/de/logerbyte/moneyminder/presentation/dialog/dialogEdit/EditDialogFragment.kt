package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogViewListener
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject


class EditDialogFragment : BaseDialogFragment(), EditDialogCallback {

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    @Inject
    lateinit var editDialogViewModel: EditDialogViewModel

    lateinit var cashView: View
    lateinit var cash: DayExpenseViewItem
    lateinit var editDialogViewModel1: EditDialogViewModel1

//    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
//        editDialogViewModel1 = EditDialogViewModel1(expenseRepo, this, context, editDialogViewModel, this)
//        cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
//        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = editDialogViewModel }
//        viewBinding.dialogContainer.addView(cashView)
//        editDialogViewModel.cashViewItem = DayExpenseViewItem("cash",""",""","","","")
//        editDialogViewModel1.setAdapter(adapterCallback)
//    }

    companion object{
        private const val CASH_ITEM = "cash_item"
        fun newInstance(dayExpenseViewItem: DayExpenseViewItem): EditDialogFragment{
            val args = Bundle()
            args.putParcelable(CASH_ITEM,dayExpenseViewItem)
            return EditDialogFragment().apply { arguments = args }
        }
    }

    override fun initCategories(categories: ArrayList<String>) {
        custom_searchlist.list = categories
    }

    override fun viewContent(): View {
         val binding = DataBindingUtil.inflate<FrameCashBinding>(layoutInflater, R.layout.frame_cash, null, false)
         arguments?.getParcelable<DayExpenseViewItem>(CASH_ITEM).apply { binding.viewItem = this }
        return binding.root
    }
}