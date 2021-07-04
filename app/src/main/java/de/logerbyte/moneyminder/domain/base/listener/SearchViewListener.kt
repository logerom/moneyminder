package de.logerbyte.moneyminder.domain.base.listener

import android.widget.SearchView

abstract class SearchViewListener : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?) = true
}
