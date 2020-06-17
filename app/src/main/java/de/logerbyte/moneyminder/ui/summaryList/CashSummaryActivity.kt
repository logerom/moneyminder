package de.logerbyte.moneyminder.ui.summaryList

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment
import de.logerbyte.moneyminder.data.SharedPrefManager
import de.logerbyte.moneyminder.databinding.ActivityMainBinding
import de.logerbyte.moneyminder.databinding.MenuBudgetBinding
import de.logerbyte.moneyminder.editDialog.EditDialogFragment
import de.logerbyte.moneyminder.menu.MenuVm
import de.logerbyte.moneyminder.screens.cashsummary.ViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel.ActivityListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CashSummaryActivity : DaggerAppCompatActivity(), ActivityListener, ViewListener {
    private lateinit var popUpWindow: PopupWindow
    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var cashSummaryViewModel: CashSummaryViewModel

    @Inject
    lateinit var menu: MenuVm

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        bindView()
        initActionBar()
        initPopUp()
    }

    private fun initActionBar() {
        my_toolbar.inflateMenu(R.menu.meun_activity_main)
        setSupportActionBar(my_toolbar)
    }

    override fun showEditDialog(item: DayExpenseViewModel, dialogVmListener: AdapterCallBack) { //  new EditDialogFragment().show(getSupportFragmentManager(), "Base_Dialog");
        val baseDialog = EditDialogFragment()
        baseDialog.show(supportFragmentManager, "Edit_Dialog")
        // TODO: 2019-09-27 implement parcelable in bundle for item transaction between fragment
        baseDialog.cash = item
        baseDialog.adapterCallback = dialogVmListener
    }

    override fun onCLickFab() {
        val adapterCallBack = binding!!.rvCosts.adapter as AdapterCallBack?
        val cashDialog = AddCashDialogFragment()
        cashDialog.adapterCallback = adapterCallBack!!
        cashDialog.show(supportFragmentManager, ADD_CASH_DIALOG)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.meun_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.budget -> {
                popUpWindow.showAsDropDown(my_toolbar, 0, 0, Gravity.END)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun initPopUp() {
        val popUpView = layoutInflater.inflate(R.layout.menu_budget, null)
        MenuBudgetBinding.bind(popUpView).apply { vm = menu }

        popUpWindow = PopupWindow(popUpView, 300, RelativeLayout.LayoutParams.WRAP_CONTENT, true).let {
            it.setOnDismissListener { onMenuDismissed() }
            it
        }
    }

    private fun onMenuDismissed() {
        // TODO-SW: move to business logic (VM?)
        sharedPrefManager.writeString(SHARED_PREF_MENU_BUDGET, menu.text.get())
    }

    private fun bindViewModel() {
        cashSummaryViewModel!!.setCashSummaryActivity(this)
    }
    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = cashSummaryViewModel
        binding?.viewListener = this
    }


    companion object {
        private const val ADD_CASH_DIALOG = "addCashDialog"
    }
}