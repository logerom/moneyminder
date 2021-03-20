package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.presentation.dialog.BaseCashDialogFragment
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment : BaseCashDialogFragment() {

    private lateinit var binding: FrameCashBinding

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var addCashViewModel: AddCashViewModel

    override fun viewContent(): View {
        return FrameCashBinding.inflate(layoutInflater)
                .apply {
                    viewModel = addCashViewModel
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
                            it.etDate.toString(), it.etCashName.toString(), it.etCashAmount.toString(),
                            it.customSearchlist.getSearchQuery(), it.tvPerson.toString()) })
    }

    private fun initLiveData() {
        addCashViewModel.categoryList.observe(viewLifecycleOwner, Observer { setListCategories(it) })
    }

    private fun setListCategories(it: List<String>) {
        binding.customSearchlist.list = it
    }
}