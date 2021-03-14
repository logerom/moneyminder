package de.logerbyte.moneyminder.presentation.dialog.dialogFilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.viewItem.FilterDialogViewItem
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_filter.*
import javax.inject.Inject

class FilterDialog : BaseDialogFragment(), BaseDialogActionListener {
    @Inject
    lateinit var filterAdapter: FilterAdapter

    @Inject
    lateinit var filterDialogViewModel: FilterDialogViewModel

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)
        viewBinding.dialogContainer.addView(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservable()
    }

    private fun initObservable() {
        filterDialogViewModel.rawCategories.observe(this, Observer { t -> filterAdapter.items = t as MutableList<FilterDialogViewItem> })
    }

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener = this

    override fun onClickOk(view: View) {
        filterDialogViewModel.applyFilter()
        // TODO-SW: on CLick okFilter
        // TODO-SW: 13.09.2020 save filter in shared preferences? + reload list
    }

    override fun onClickCancel(view: View) {
        // TODO-SW: onClickCancel
    }

    private fun initAdapter() {
        rvFilterCategories.adapter = filterAdapter
    }
}