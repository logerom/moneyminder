package de.logerbyte.moneyminder.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerDialogFragment
import de.logerbyte.moneyminder.databinding.BaseDialogBinding


abstract class BaseDialogFragmentv1: DaggerDialogFragment() {

    lateinit var baseDialogBinding: BaseDialogBinding

    abstract fun viewContent(bundle: Bundle?): View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BaseDialogBinding.inflate(inflater).apply {
            baseDialogBinding = this
            dialogContainer.addView(viewContent(savedInstanceState))}.root
    }
}
