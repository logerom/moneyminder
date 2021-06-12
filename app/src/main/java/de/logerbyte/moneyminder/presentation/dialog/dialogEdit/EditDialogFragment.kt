package de.logerbyte.moneyminder.presentation.dialog.dialogEdit

import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.entities.data.viewData.DayExpenseViewItem
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.presentation.BaseFragment
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragmentv1
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject


class EditDialogFragment: BaseDialogFragmentv1(), BaseFragment {

    @Inject
    lateinit var editDialogViewModel: EditDialogViewModel

//    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
//        editDialogViewModel1 = EditDialogViewModel1(expenseRepo, this, context, editDialogViewModel, this)
//        cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
//        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = editDialogViewModel }
//        viewBinding.dialogContainer.addView(cashView)
//        editDialogViewModel.cashViewItem = DayExpenseViewItem("cash",""",""","","","")
//        editDialogViewModel1.setAdapter(adapterCallback)
//    }


    override fun viewContent(bundle: Bundle?): View {
        val binding = DataBindingUtil.inflate<FrameCashBinding>(layoutInflater, R.layout.frame_cash, null, false)
        val data = getParcel<DayExpenseViewItem>()
        binding.viewItem = data
        baseDialogBinding.viewListener = editDialogViewModel
        editDialogViewModel.setData(data?: DayExpenseViewItem())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
    }

    private fun initLiveData() {
        editDialogViewModel.categories.observe(viewLifecycleOwner, Observer { onCategories(it) })
        editDialogViewModel.shallDialogClose.observe(viewLifecycleOwner, Observer { closeDialog(it) })
    }

    private fun closeDialog(close: Boolean) {
        if(close)this.dismiss()
    }

    fun onCategories(list: ArrayList<String>){
        custom_searchlist.list = list
    }
}
