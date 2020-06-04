package de.logerbyte.moneyminder.addCashDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.db.AppDatabaseManager
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.viewModels.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.category_list.*
import javax.inject.Inject


class AddCashDialogFragment : BaseDialogFragment() {

    @Inject
    lateinit var appDatabaseManager: AppDatabaseManager

    lateinit var adapterCallback: AdapterCallBack
    private lateinit var addCashViewModel: AddCashViewModel
    private val cashViewModel = CashViewModel()

    var categories = mutableListOf<String>()
    lateinit var categoryAdapter: CategoryAdapter

    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return addCashViewModel
    }

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        addCashViewModel = AddCashViewModel(this, context, cashViewModel, adapterCallback, appDatabaseManager)
        val cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = cashViewModel }
        viewBinding.dialogContainer.addView(cashView)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // fixme: RxJava2 for room + need to return boolean?
        // todo move to vm
        // todo dagger
        appDatabaseManager.categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categoryList ->
                    showCategories(categoryList)
                    categories = categoryList
                }

        // TODO-SW: extract into own compound view
        searchView.apply {
            setOnQueryTextListener(object : SearchViewListener() {
                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.apply {
                        val list = categoryAdapter.originalItems.filter { category -> category.startsWith(this, true) }
                        categoryAdapter.items = list as ArrayList
                        cashViewModel.cashCategory = newText
                    }
                    return false
                }
            })
            isIconified = false
            setOnCloseListener { true }
        }
    }

    private fun showCategories(categories: List<String>) {
        searchViewList.adapter = CategoryAdapter(clickListener = { s -> searchView.setQuery(s, false) })
                .apply {
                    originalItems = categories as ArrayList
                    categoryAdapter = this
                }
    }
}