package de.logerbyte.moneyminder.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.Dialog1Binding


abstract class BaseDialog : DialogFragment(), BaseDialogViewListener {

    protected lateinit var binding: Dialog1Binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return createDialog()
    }

    protected fun createDialog(): Dialog {
        return AlertDialog.Builder(activity).let {
            it.setView(initBinding()!!.root)
            it.create()
        }
    }

    private fun initBinding(): Dialog1Binding? {
        binding = createBinding(R.layout.dialog1) as Dialog1Binding
        additionalBinding()
        binding!!.dialogViewModelListener = setViewModelListener()
        return binding
    }

    abstract fun additionalBinding()

    protected fun createBinding(layout: Int): ViewDataBinding {
        return DataBindingUtil.inflate(activity!!.layoutInflater, layout, null, false)
    }

    abstract fun setViewModelListener(): BaseDialog1ViewModelListener

    override fun dismissDialog() {
        dismiss()
    }
}