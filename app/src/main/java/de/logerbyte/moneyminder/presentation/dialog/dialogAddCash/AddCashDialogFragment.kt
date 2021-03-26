package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.presentation.dialog.BaseCashDialogFragment
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment : BaseCashDialogFragment<DayExpenseViewItem>() {

    private lateinit var binding: FrameCashBinding

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var addCashViewModel: AddCashViewModel

    override fun viewContent(bundle: Bundle?): View {
        return FrameCashBinding.inflate(layoutInflater)
                .apply {
                    viewItem = addCashViewModel.cashViewItem
                    binding = this
                }
                .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
    }

    override fun onOKClicked(view: View) {
        super.onOKClicked(view)
        addCashViewModel.saveCash(
                binding.let {
                    // Todo X: set CashViewitem over Databinding
                    CashViewItem(
                            it.etDate.text.toString(), it.etCashName.text.toString(), it.etCashAmount.text.toString(),
                            it.customSearchlist.getSearchQuery(), it.etPerson.text.toString()) })
    }

    private fun initLiveData() {
        addCashViewModel.categoryList.observe(viewLifecycleOwner, Observer { setListCategories(it) })
    }

    private fun setListCategories(it: List<String>) {
        binding.customSearchlist.list = it
    }
}