package de.logerbyte.moneyminder.cashOverview.menu.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_filter.*
import javax.inject.Inject

class FilterDialog : BaseDialogFragment(), BaseDialogActionListener {
    @Inject
    lateinit var filterAdapter: FilterAdapter

    @Inject
    lateinit var filterDialogVM: FilterDialogVM

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
        filterDialogVM.rawCategoriess.observe(this, Observer { t -> filterAdapter.items = t as MutableList<FilterDialogItem> })
    }

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener = this

    override fun onClickOk(view: View) {
        filterDialogVM.applyFilter()
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