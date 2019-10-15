package de.logerbyte.moneyminder.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat


class FloatingImageViewBehavior : CoordinatorLayout.Behavior<FloatingImageView> {
    constructor() : super() // Used when the layout has a behavior attachedd via the Annotation
    // Used when the layout has a behavior attached via xml app:layout_behavior=".link.to.your
    // .behavior")
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingImageView, directTargetChild: View, target: View, axes: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingImageView, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed)
        child.translationY += dyConsumed.toFloat()
    }
}