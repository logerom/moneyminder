package de.logerbyte.moneyminder.presentation.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerDialogFragment
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.databinding.BaseDialogBinding

abstract class BaseDialogFragment<PARCEL_TYPE: Parcelable> : DaggerDialogFragment() {
    var parcelKey: String? = null
    var parcel: PARCEL_TYPE? = null
        get() = arguments?.getParcelable(parcelKey)

    open fun onCancelClicked(view: View) {
        this.dismiss()
    }
    open fun onOKClicked(view: View) {
        this.dismiss()
    }

    abstract fun viewContent(bundle: Bundle?): View

    companion object {
        fun <TYPE: Parcelable>newInstancee(viewItem: DayExpenseViewItem, parcelKey: String, fragment: BaseDialogFragment<TYPE>) = fragment.apply {
            this.arguments = Bundle().also { it.putParcelable(parcelKey, viewItem) }
            this.parcelKey = parcelKey
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("parcel_key", parcelKey)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if(parcelKey.isNullOrBlank()) parcelKey = savedInstanceState?.getString("parcel_key")
        return BaseDialogBinding.inflate(inflater).apply {
            dialogContainer.addView(viewContent(savedInstanceState))
            buCancel.setOnClickListener { onCancelClicked(it) }
            buOK.setOnClickListener { onOKClicked(it) }
        }.root
    }


}