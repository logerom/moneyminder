package de.logerbyte.moneyminder.base

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.addCashDialog.CategoryAdapter
import kotlinx.android.synthetic.main.category_list.view.*

class SearchListView(context: Context?, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    var list = ArrayList<String>()
        set(value) {
            categoryAdapter.originalItems = value
            field = value
        }

    lateinit var categoryAdapter: CategoryAdapter

    init {
        initLayout(context)
        initAdapter()
        initSearchView()
    }

    fun getSearchQuery(): String {
        return searchView.query.toString()
    }

    fun setSearchQuery(query: String) {
        searchView.setQuery(query, false)
    }

    private fun initSearchView() {
        searchView.apply {
            isIconified = false
            setOnCloseListener { true }
        }
    }

    private fun initLayout(context: Context?) {
        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        layoutInflater.inflate(R.layout.category_list, this, true)
    }

    private fun initAdapter() {
        searchViewList.adapter = CategoryAdapter(clickListener = { s -> searchView.setQuery(s, false) })
                .apply {
                    categoryAdapter = this
                }
    }
}