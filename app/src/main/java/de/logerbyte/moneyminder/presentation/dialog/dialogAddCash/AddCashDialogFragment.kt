package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.presentation.dialog.BaseCashDialogFragment
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment : BaseCashDialogFragment() {

    private lateinit var binding: FrameCashBinding

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var addCashViewModel: AddCashViewModel

    override val onOKClicked = {}
    override val onCancelClicked = {}

    override fun viewContent(): View {
        return FrameCashBinding.inflate(layoutInflater)
            .apply {
                viewModel = addCashViewModel

                binding = this}
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
    }

    private fun initLiveData() {
        addCashViewModel.categoryList.observe(viewLifecycleOwner, Observer { setListCategories(it) })
    }

    private fun setListCategories(it: List<String>) {
        binding.customSearchlist.list = it
    }
}