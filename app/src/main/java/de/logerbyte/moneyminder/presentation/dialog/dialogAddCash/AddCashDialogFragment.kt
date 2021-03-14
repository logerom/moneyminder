package de.logerbyte.moneyminder.presentation.dialog.dialogAddCash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogViewModel
import de.logerbyte.moneyminder.domain.database.expense.ExpenseRepo
import de.logerbyte.moneyminder.databinding.BaseDialogBinding
import de.logerbyte.moneyminder.databinding.FrameCashBinding
import de.logerbyte.moneyminder.dialogs.BaseDialogActionListener
import de.logerbyte.moneyminder.dialogs.BaseDialogFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.frame_cash.*
import javax.inject.Inject
import javax.inject.Named


class AddCashDialogFragment : BaseDialogFragment() {

    @Inject
    lateinit var expenseRepo: ExpenseRepo

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var editDialogViewModel: EditDialogViewModel

    lateinit var adapterCallback: AdapterCallBack

    private lateinit var addCashViewModel: AddCashViewModel

    override fun setDialogBaseActionButtonListener(): BaseDialogActionListener {
        return addCashViewModel
    }

    override fun additionalContentViewBinding(viewBinding: BaseDialogBinding) {
        // TODO: 13.03.21 inject addcashviewmodel
        addCashViewModel = AddCashViewModel(this, context, editDialogViewModel, adapterCallback, expenseRepo)
        val cashView = LayoutInflater.from(context).inflate(R.layout.frame_cash, null, false)
        DataBindingUtil.bind<FrameCashBinding>(cashView!!).let { it!!.viewModel = editDialogViewModel }
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
        expenseRepo.categories
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { categoryList -> custom_searchlist.list = categoryList as ArrayList<String> }
    }
}