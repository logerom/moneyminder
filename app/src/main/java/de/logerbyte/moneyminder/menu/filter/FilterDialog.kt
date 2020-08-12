package de.logerbyte.moneyminder.menu.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_filter.*
import javax.inject.Inject

// TODO-SW: open filter form menu
class FilterDialog : BaseDialogFragment(), BaseDialogActionListener {
    // TODO-SW: inject VM  and bind to View
    @Inject
    lateinit var filterAdapter: FilterAdapter

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)
        viewBinding.dialogContainer.addView(view)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener = this

    override fun onClickOk(view: View) {
        // TODO-SW: on CLick okFilter
        TODO("Not yet implemented")
    }

    override fun onClickCancel(view: View) {
        // TODO-SW: onClickCancel
    }

    private fun initAdapter() {
        rvFilterCategories.adapter = filterAdapter
    }
}