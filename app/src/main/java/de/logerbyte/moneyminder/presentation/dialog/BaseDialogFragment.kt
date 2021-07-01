package de.logerbyte.moneyminder.presentation.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.android.support.DaggerDialogFragment
import de.logerbyte.moneyminder.entities.data.viewData.CashViewItem
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.presentation.PARCEL_KEY

@Deprecated("Use BaseDialogFragmentv1")
abstract class BaseDialogFragment<PARCEL_TYPE: Parcelable> : DaggerDialogFragment() {
    var parcelKey: String? = null
    var parcel: PARCEL_TYPE? = null
        get() = arguments?.getParcelable(PARCEL_KEY)

    open fun onCancelClicked(view: View) {
        this.dismiss()
    }
    open fun onOKClicked(view: View) {
        this.dismiss()
    }

    abstract fun viewContent(bundle: Bundle?): View

    companion object MyFragmet: Fragment(){
        fun <TYPE: Parcelable>newBaseInstancee(viewItem: CashViewItem, fragment: BaseDialogFragment<TYPE>) = fragment.apply {
            this.arguments = Bundle().also { it.putParcelable(PARCEL_KEY, viewItem) }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return BaseDialogBinding.inflate(inflater).apply {
            dialogContainer.addView(viewContent(savedInstanceState))
            buCancel.setOnClickListener { onCancelClicked(it) }
            buOK.setOnClickListener { onOKClicked(it) }
        }.root
    }


}
