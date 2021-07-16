package de.logerbyte.moneyminder.domain.android

import android.view.View

fun setVisibilityWithFeature(view: View, featureFlag: Boolean){
    if(featureFlag)
        view.visibility = View.VISIBLE
    else
        view.visibility = View.INVISIBLE
}
