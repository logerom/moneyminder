package de.logerbyte.moneyminder.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding


abstract class BaseDialog : DialogFragment(), DialogCallback {

    protected lateinit var binding: BaseDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createDialog()
    }

    private fun createDialog(): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setView(initBinding()!!.root)
            it.create()
        }
    }

    private fun initBinding(): BaseDialogBinding? {
        binding = createBinding(R.layout.base_dialog) as BaseDialogBinding
        createContentBinding()
        binding!!.dialogViewModelListener = setDialogBaseActionButtonListener()
        return binding
    }

    abstract fun createContentBinding()

    private fun createBinding(layout: Int): ViewDataBinding {
        return DataBindingUtil.inflate(activity!!.layoutInflater, layout, null, false)
    }

    abstract fun setDialogBaseActionButtonListener(): DialogViewListener

    override fun dismissDialog() {
        dismiss()
    }

    interface ViewListener {
        fun onCLickOK()
        fun onClickCancel()
    }
}