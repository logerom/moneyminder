package de.logerbyte.moneyminder.addCashDialog

import android.widget.SearchView
import androidx.databinding.BindingAdapter

@BindingAdapter("textChangeListener")
fun setTextChangeListener(searchView: SearchView, viewModel: AddCashViewModel) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            viewModel.submittedQueryText.value = query
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            viewModel.changedQueryText = newText
            return true
        }
    })
}
