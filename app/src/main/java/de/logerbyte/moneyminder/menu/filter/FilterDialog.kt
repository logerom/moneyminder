package de.logerbyte.moneyminder.menu.filter

import android.view.LayoutInflater
import android.view.View
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import javax.inject.Inject

// TODO-SW: open filter form menu
class FilterDialog @Inject constructor() : BaseDialogFragment(), BaseDialogActionListener {
    // TODO-SW: inject VM  and bind to View
    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_filter, null)
        viewBinding.dialogContainer.addView(view)
    }

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener = this

    override fun onClickOk(view: View) {
        TODO("Not yet implemented")
    }

    override fun onClickCancel(view: View) {

    }
}