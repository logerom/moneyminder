package de.logerbyte.moneyminder.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter


class FloatingActionButtonBehavior : CoordinatorLayout.Behavior<FloatingActionButton> {
    constructor() : super() // Used when the layout has a behavior attachedd via the Annotation
    // Used when the layout has a behavior attached via xml app:layout_behavior=".link.to.your
    // .behavior")
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton,
                                     directTargetChild: View, target: View, axes: Int): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingActionButton, target: View,
                                dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int) {

        Log.i("Scroll", "dyConsumed: $dyConsumed dyUnconSumed: $dyUnconsumed")

        crollInDependencyOfCashadapter(target, child, dyConsumed)
    }

    private fun crollInDependencyOfCashadapter(target: View, child: FloatingActionButton, dyConsumed: Int) {
        val adapter = (target as RecyclerView).adapter as CashAdapter
        if (adapter.floating) child.translationY += dyConsumed.toFloat()
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        return dependency.findViewById<RecyclerView>(R.id.rv_costs) != null
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: FloatingActionButton, dependency: View): Boolean {
        Log.i("Scroll", "onDependentViewChanged")
        val cashAdapter = dependency.findViewById<RecyclerView>(R.id.rv_costs)?.let {
            it.adapter as CashAdapter
        }
        if (cashAdapter != null) {
            if (cashAdapter.floating) {
                if (cashAdapter.dependencyView.visibility != View.VISIBLE) {
                    child.hide()
                } else {
                    child.show()
                }
                val id = cashAdapter
                        .floatingDepedencyViewID
                (child.layoutParams as CoordinatorLayout.LayoutParams).anchorId = id
            } else {
                (child.layoutParams as CoordinatorLayout.LayoutParams).anchorId = View.NO_ID
            }
        }

//
//
//
//        val rootView = recView.parent.parent.parent as CoordinatorLayout
//        Log.i("Float" , "viewHolderRoot: $rootView")
//        val floatingImageContainer = rootView.findViewById<FloatingActionButton>(R.id.floating_image_Container)
//        val layoutParams = floatingImageContainer.layoutParams as CoordinatorLayout.LayoutParams
//        layoutParams.anchorId = viewHolder.itemView.id
//        layoutParams.anchorGravity = Gravity.BOTTOM
////        val adapter = (dependency as RecyclerView).adapter as CashAdapter
////        if(adapter.floating) child.translationY += dyConsumed.toFloat()
        return true
    }
}