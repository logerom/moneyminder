package de.logerbyte.moneyminder.presentation

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment

const val PARCEL_KEY = "parcel_key"

/**
 * Base fragment interface with parcelable function for dialog fragment and normal fragment.
 */
interface BaseFragment {
    fun <PARCEL_TYPE : Parcelable> Fragment.getParcel() = arguments?.getParcelable<PARCEL_TYPE>(PARCEL_KEY)

    companion object{
        fun <TYPE : Parcelable, FRAGMENT: Fragment>newInstanceWithArguments(viewItem: TYPE, fragment: FRAGMENT) = fragment.apply {
            this.arguments = Bundle().also { it.putParcelable(PARCEL_KEY, viewItem) }
        }
    }
}
