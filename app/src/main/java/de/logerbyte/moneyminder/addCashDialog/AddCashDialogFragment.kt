package de.logerbyte.moneyminder.addCashDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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


class AddCashDialogFragment : BaseDialogFragment() {

    lateinit var adapterCallback: AdapterCallBack
    private lateinit var addCashViewModel: AddCashViewModel
    private val cashViewModel = CashViewModel()

    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return addCashViewModel
    }

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        addCashViewModel = AddCashViewModel(this, context, cashViewModel, adapterCallback)
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

        var categories = mutableListOf<String>()
        // fixme: RxJava2 for room + need to return boolean?
        // todo move to vm
        // todo dagger
        AppDatabaseManager(context).categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t -> showCategories(t) }


        // TODO: add categories from db
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
    }

    private fun showCategories(categories: List<String>) {
        searchViewList.adapter = CategoryAdapter()
                .apply { items = categories as ArrayList }
    }
}