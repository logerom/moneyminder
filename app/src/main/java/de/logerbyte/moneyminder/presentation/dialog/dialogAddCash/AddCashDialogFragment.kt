package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.domain.showToastAlert
import de.logerbyte.moneyminder.presentation.BaseFragment
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragmentv1
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment: BaseDialogFragmentv1(), BaseFragment{

    private lateinit var binding: FrameCashBinding

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var addCashViewModel: AddCashViewModel

    override fun viewContent(bundle: Bundle?): View {
        baseDialogBinding.viewListener = addCashViewModel
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

    private fun initLiveData() {
        addCashViewModel.categoryList.observe(viewLifecycleOwner, Observer { setListCategories(it) })
        addCashViewModel.isInputCorrect.observe(viewLifecycleOwner, Observer { handleInput(it) })
        addCashViewModel.closeDialog.observe(viewLifecycleOwner, Observer { this.dismiss() })
    }

    private fun handleInput(it: Boolean?) {
        it?.also {
            if(it) dismiss() else context.showToastAlert("Input not correct") }
    }

    private fun setListCategories(it: List<String>) {
        binding.customSearchlist.list = it
    }
}
