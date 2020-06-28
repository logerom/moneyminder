package de.logerbyte.moneyminder.base.listener

import android.widget.SearchView

abstract class SearchViewListener : SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?) = true

    override fun onQueryTextChange(newText: String?) = true
}