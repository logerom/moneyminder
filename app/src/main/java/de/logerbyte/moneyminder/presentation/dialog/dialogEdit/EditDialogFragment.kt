package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.FrameCashBinding
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

    companion object {
        fun newInstance(viewItem: DayExpenseViewItem, parcelKey: String, fragment: BaseDialogFragment) = fragment.apply {
            this.arguments = Bundle().also { it.putParcelable(parcelKey, viewItem) }
            this.parcelKey = parcelKey
        }
    }

    override fun initCategories(categories: ArrayList<String>) {
        custom_searchlist.list = categories
    }

    override fun viewContent(): View {
        val binding = DataBindingUtil.inflate<FrameCashBinding>(layoutInflater, R.layout.frame_cash, null, false)
        arguments?.getParcelable<DayExpenseViewItem>(parcelKey).apply { binding.viewItem = this }
        return binding.root
    }
}