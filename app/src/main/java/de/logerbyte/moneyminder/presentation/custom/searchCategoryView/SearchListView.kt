package de.logerbyte.moneyminder.presentation.custom.searchCategoryView

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.base.listener.SearchViewListener
import kotlinx.android.synthetic.main.category_list.view.*

class SearchListView(context: Context?, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    var list = listOf<String>()
        set(value) {
            categoryAdapter.items = value
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
        searchView.setOnQueryTextListener(object : SearchViewListener() {
            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.apply {
                    val list = list.filter { category -> category.startsWith(this, true) }
                    // todo x: onQueryChange action
//                    searchViewView.categoryAdapter.items = list as ArrayList
//                    listener?.onChange()
                }
                return true
            }
        })
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