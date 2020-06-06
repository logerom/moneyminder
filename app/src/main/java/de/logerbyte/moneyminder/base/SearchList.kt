package de.logerbyte.moneyminder.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import de.logerbyte.moneyminder.R

class SearchList(context: Context?, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    init {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.category_list, this, true)
    }
}