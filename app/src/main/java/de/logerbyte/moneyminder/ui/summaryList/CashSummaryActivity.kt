package de.logerbyte.moneyminder.ui.summaryList

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import dagger.android.support.DaggerAppCompatActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.SHARED_PREF_MENU_BUDGET
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment
import de.logerbyte.moneyminder.data.SharedPrefManager
import de.logerbyte.moneyminder.databinding.ActivityMainBinding
import de.logerbyte.moneyminder.databinding.MenuSettingsBindingImpl
import de.logerbyte.moneyminder.editDialog.EditDialogFragment
import de.logerbyte.moneyminder.menu.MenuVm
import de.logerbyte.moneyminder.menu.SettingsPopupWindow
import de.logerbyte.moneyminder.screens.cashsummary.ViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel.ActivityListener
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class CashSummaryActivity : DaggerAppCompatActivity(), ActivityListener, ViewListener {
    private lateinit var settingsWindowDelegator: SettingsPopupWindow
    private var binding: ActivityMainBinding? = null

    @Inject
    lateinit var cashSummaryViewModel: CashSummaryViewModel

    @Inject
    lateinit var menu: MenuVm

    @Inject
    lateinit var sharedPrefManager: SharedPrefManager

    @Inject
    lateinit var settingsPopupWindow: SettingsPopupWindow

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
            R.id.settings -> {
                settingsWindowDelegator.popupWindow.showAsDropDown(my_toolbar, 0, 0, Gravity.END)
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun initPopUp() {
        val popUpView = layoutInflater.inflate(R.layout.menu_settings, null)
        // TODO-SW: inject setttingspopUP
        settingsWindowDelegator = settingsPopupWindow.createPopupWindow(popUpView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        MenuSettingsBindingImpl.bind(popUpView).apply {
            vm = menu
            listener = settingsWindowDelegator
        }
    }

    // TODO-SW: move to settingsPopupWindow
    private fun onMenuDismissed() {
        // TODO: move to business logic (VM?)
        // Error-SW: crashed when enter
        menu.budget.get()?.apply {
            sharedPrefManager.writeSharedPrefInt(SHARED_PREF_MENU_BUDGET, this)
        }
        cashSummaryViewModel.cashAdapter.onBudgetUpdated()
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