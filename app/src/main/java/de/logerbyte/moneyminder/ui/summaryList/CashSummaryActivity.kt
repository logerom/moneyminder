package de.logerbyte.moneyminder.ui.summaryList

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupWindow
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.DaggerAppCompatActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment
import de.logerbyte.moneyminder.databinding.ActivityMainBinding
import de.logerbyte.moneyminder.editDialog.EditDialogFragment
import de.logerbyte.moneyminder.screens.cashsummary.ViewListener
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel.ActivityListener
import kotlinx.android.synthetic.main.activity_main.*

class CashSummaryActivity : DaggerAppCompatActivity(), ActivityListener, ViewListener {
    private var cashSummaryViewModel: CashSummaryViewModel? = null
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        bindView()
        initActionBar()
    }

    private fun initActionBar() {
        my_toolbar.inflateMenu(R.menu.meun_activity_main)
        setSupportActionBar(my_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.meun_activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.budget -> {
                showPopUp()
                true
            }
            else -> super.onOptionsItemSelected(item)

        }
    }

    private fun showPopUp() {
        val popUpView = layoutInflater.inflate(R.layout.menu_budget, null)
        val popup = PopupWindow(popUpView, 300, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        popup.showAsDropDown(my_toolbar, 0, 0, Gravity.END)
        // TODO-SW: view binding for menu item
        // TODO-SW: add budget to shared pref
    }

    private fun bindViewModel() {
        cashSummaryViewModel = ViewModelProviders.of(this).get(CashSummaryViewModel::class.java)
        cashSummaryViewModel!!.setCashSummaryActivity(this)
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = cashSummaryViewModel
        binding?.viewListener = this
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

    companion object {
        private const val ADD_CASH_DIALOG = "addCashDialog"
    }
}