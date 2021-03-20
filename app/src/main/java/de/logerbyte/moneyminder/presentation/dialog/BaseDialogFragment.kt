package de.logerbyte.moneyminder.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerDialogFragment
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import kotlinx.android.synthetic.main.base_dialog.*


abstract class BaseDialogFragment : DaggerDialogFragment() {

    abstract val onOKClicked: ()->Unit
    abstract val onCancelClicked: ()->Unit
    abstract fun viewContent(): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BaseDialogBinding.inflate(inflater).apply {
            dialogContainer.addView(viewContent())
            buCancel.setOnClickListener { onOKClicked }
            buOK.setOnClickListener { onCancelClicked }
        }.root
    }
}