package de.logerbyte.moneyminder.dialogs.editDialog

import android.app.AlertDialog
import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.Dialog1Binding
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewListener
import de.logerbyte.moneyminder.dialogs.BaseDialogViewModel1.BaseDialogViewModel1


class BaseDialog1 : DialogFragment(), BaseDialog1ViewListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DataBindingUtil.inflate<Dialog1Binding>(activity!!.layoutInflater, R.layout
                .dialog1, null, false)
        binding.dialogViewModelListener = BaseDialogViewModel1(this)
        return AlertDialog.Builder(activity).let {
            it.setView(binding.root)
            it.create()
        }
    }

    override fun dismissDialog() {
        dismiss()
    }
}