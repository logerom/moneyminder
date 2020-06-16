package de.logerbyte.moneyminder.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import de.logerbyte.moneyminder.addCashDialog.SearchViewListener
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
                return false
            }
        })
    }

    @JvmStatic
    @BindingAdapter("addTextChangeListener")
    fun addTextChangeListener(view: EditText, vm: EditTextVM) {
        view.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                vm.text.set(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        })
    }

}
