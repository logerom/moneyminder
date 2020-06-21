package de.logerbyte.moneyminder.addCashDialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.data.db.AppDatabaseManager
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import de.logerbyte.moneyminder.dialogs.DialogViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.viewModels.CashViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment : BaseDialogFragment() {

    @Inject
    lateinit var appDatabaseManager: AppDatabaseManager

    @Inject
    @Named("Date")
    lateinit var date: String
    lateinit var cashViewModel: CashViewModel

    lateinit var adapterCallback: AdapterCallBack
    private lateinit var addCashViewModel: AddCashViewModel

    override fun setDialogBaseActionButtonListener(): DialogViewListener {
        return addCashViewModel
    }

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        cashViewModel = CashViewModel(date)
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
                .subscribe { categoryList -> custom_searchlist.list = categoryList as ArrayList<String> }
    }
}