package de.logerbyte.moneyminder.dialogs.addCashDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.viewModels.CashViewModel
import kotlinx.android.synthetic.main.category_list.*


class AddCashDialogFragment : BaseDialogFragment() {


    // todo-stewo: 2019-11-26 add cash layout + viewbinding

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
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        return view
    }


}