package de.logerbyte.moneyminder.domain

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.ObservableField
import de.logerbyte.moneyminder.domain.base.listener.SearchViewListener
import de.logerbyte.moneyminder.presentation.custom.searchCategoryView.SearchListView
import kotlinx.android.synthetic.main.category_list.view.*

/**
 * InverseBindingAdapter with InverseBindingListener for 2 way binding properties.
 */
object BindingAdapter {

    @JvmStatic
    @BindingAdapter("searchText")
    fun setSearchText(searchViewView: SearchListView, value: ObservableField<String>) {
        value.get()?.apply {searchViewView.setSearchQuery(this)}
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "searchText")
    fun getSearchText(view: SearchListView): String {
        return view.getSearchQuery()
    }

    @JvmStatic
    @BindingAdapter("searchTextAttrChanged")
    fun setListener(searchViewView: SearchListView, listener: InverseBindingListener?) {
        searchViewView.searchView.setOnQueryTextListener(object : SearchViewListener() {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.apply {
                    val list = searchViewView.list.filter { category -> category.startsWith(this, true) }
                    searchViewView.categoryAdapter.items = list as ArrayList
                    listener?.onChange()
                }
                return true
            }
        })
    }
}
