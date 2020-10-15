package de.logerbyte.moneyminder.base

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import de.logerbyte.moneyminder.base.listener.SearchViewListener
import de.logerbyte.moneyminder.compoundView.searchcategoryList.SearchListView
import kotlinx.android.synthetic.main.category_list.view.*

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("searchText")
    fun setSearchText(searchViewView: SearchListView, value: String) {
        searchViewView.setSearchQuery(value)
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
                    val list = searchViewView.categoryAdapter.originalItems.filter { category -> category.startsWith(this, true) }
                    searchViewView.categoryAdapter.items = list as ArrayList
                    listener?.onChange()
                }
                return true
            }
        })
    }
}
