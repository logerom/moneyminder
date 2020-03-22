package de.logerbyte.moneyminder.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding


abstract class BaseDialogFragment : DialogFragment(), DialogCallback {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewBinding = DataBindingUtil.inflate(inflater, R.layout.base_dialog, container, false)
                as BaseDialogBinding
        additionalContentViewBinding(viewBinding)
        viewBinding!!.dialogViewModelListener = setDialogBaseActionButtonListener()
        return viewBinding.root
    }


//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        return createDialog()
//    }
//
//    private fun createDialog(): Dialog {
//        return AlertDialog.Builder(activity).let {
//            it.setView(initBinding()!!.root)
//            it.create()
//        }
//    }

//    private fun initBinding(): BaseDialogBinding? {
//        binding = createBinding(R.layout.base_dialog) as BaseDialogBinding
//        additionalContentViewBinding(viewBinding)
//        binding!!.dialogViewModelListener = setDialogBaseActionButtonListener()
//        return binding
//    }

    abstract fun additionalContentViewBinding(viewBinding: BaseDialogBinding)

//    private fun createBinding(layout: Int): ViewDataBinding {
//        return DataBindingUtil.inflate(activity!!.layoutInflater, layout, null, false)
//    }

    abstract fun setDialogBaseActionButtonListener(): DialogViewListener

    override fun dismissDialog() {
        dismiss()
    }

    interface ViewListener {
        fun onCLickOK()
        fun onClickCancel()
    }
}