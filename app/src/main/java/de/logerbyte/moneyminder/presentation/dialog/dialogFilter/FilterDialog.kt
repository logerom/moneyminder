package de.logerbyte.moneyminder.presentation.dialog.dialogFilter

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.entities.data.viewData.FilterDialogViewItem
import de.logerbyte.moneyminder.dialogs.BaseDialogViewListener
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_filter.*
import javax.inject.Inject

class FilterDialog : BaseDialogFragment<Parcelable>(), BaseDialogViewListener {
    @Inject
    lateinit var filterAdapter: FilterAdapter

    @Inject
    lateinit var filterDialogViewModel: FilterDialogViewModel

    override fun viewContent(bundle: Bundle?) = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservable()
    }

    private fun initObservable() {
        filterDialogViewModel.rawCategories.observe(this, Observer { t -> filterAdapter.items = t as MutableList<FilterDialogViewItem> })
    }

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