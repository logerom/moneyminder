package de.logerbyte.moneyminder.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.presentation.dialog.BaseDialogFragment

const val PARCEL_KEY = "parcel_key"

/**
 * Base fragment interface with parcelable function for dialog fragment and normal fragment.
 */
interface BaseFragment {
    fun <PARCEL_TYPE : Parcelable> Fragment.getParcel() = arguments?.getParcelable<PARCEL_TYPE>(PARCEL_KEY)

    companion object{
        fun <TYPE : Parcelable, FRAGMENT: Fragment>newInstancee(viewItem: TYPE, fragment: FRAGMENT) = fragment.apply {
            this.arguments = Bundle().also { it.putParcelable(PARCEL_KEY, viewItem) }
        }
    }
}