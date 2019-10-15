package de.logerbyte.moneyminder.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import androidx.coordinatorlayout.widget.CoordinatorLayout

@CoordinatorLayout.DefaultBehavior(FloatingImageViewBehavior::class)
class FloatingImageView : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
}