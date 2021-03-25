package de.logerbyte.moneyminder.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerDialogFragment
import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.databinding.BaseDialogBinding

abstract class BaseDialogFragment : DaggerDialogFragment() {
    lateinit var parcelKey: String


    open fun onCancelClicked(view: View) {
        this.dismiss()
    }
    open fun onOKClicked(view: View) {
        this.dismiss()
    }

    abstract fun viewContent(): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BaseDialogBinding.inflate(inflater).apply {
            dialogContainer.addView(viewContent())
            buCancel.setOnClickListener { onCancelClicked(it) }
            buOK.setOnClickListener { onOKClicked(it) }
        }.root
    }


}