package de.logerbyte.moneyminder.presentation.activityCashSummary

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.presentation.dialog.dialogAddCash.AddCashDialogFragment
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogFragment
import de.logerbyte.moneyminder.presentation.custom.settingsPopupWindow.SettingsPopupViewModel
import de.logerbyte.moneyminder.cashOverview.menu.SettingsPopupWindow
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewItem
import de.logerbyte.moneyminder.data.viewItem.DayExpenseViewViewItem
import de.logerbyte.moneyminder.data.viewItem.ExpenseListViewItem
import de.logerbyte.moneyminder.databinding.ActivityMainBinding
import de.logerbyte.moneyminder.databinding.MenuSettingsBinding
import de.logerbyte.moneyminder.presentation.cashadapter.CashAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class CashSummaryActivity : DaggerAppCompatActivity(), DayExpenseViewViewItem.ActivityListener, CashSummaryViewListener {
    private lateinit var settingsWindowDelegator: SettingsPopupWindow
    private var binding: ActivityMainBinding? = null

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var cashSummaryViewModel: CashSummaryViewModel

    @Inject
    lateinit var settingsPopupViewModel: SettingsPopupViewModel

    @Inject
    lateinit var settingsPopupWindow: SettingsPopupWindow

    lateinit var cashAdapter: CashAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindContentView()
        initActionBar()
        initPopUp()
        setUpRecyclerView()
        initLiveData()
    }

    private fun setUpRecyclerView() {
        binding?.rvCosts?.apply {
            this.adapter = CashAdapter(onDeleteClicked = openDeleteDialog())
                            .apply { cashAdapter = this }
            this.layoutManager = LinearLayoutManager(this.context)
        }
    }

    private fun openDeleteDialog(): () -> Unit {
//        todo X: open delete dialog
        return {}
    }

    private fun initLiveData() {
        cashSummaryViewModel.observeExpenses().observe(this, Observer { setListAdapter(it) })
    }

    private fun setListAdapter(it: List<ExpenseListViewItem>) {
        cashAdapter.items = it
    }

    private fun initActionBar() {
        my_toolbar.inflateMenu(R.menu.meun_activity_main)
        setSupportActionBar(my_toolbar)
    }

    override fun showEditDialog(item: DayExpenseViewItem, dialogVmListener: AdapterCallBack) {
        //  new EditDialogFragment().show
        // (getSupportFragmentManager(), "Base_Dialog");
        val baseDialog = EditDialogFragment()
        baseDialog.show(supportFragmentManager, "Edit_Dialog")
        // TODO: 2019-09-27 implement parcelable in bundle for item transaction between fragment
        baseDialog.cash = item
        baseDialog.adapterCallback = dialogVmListener
    }

    override fun onCLickFab() {
        val cashDialog = AddCashDialogFragment()
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

    //    todo X: Savings doesnt work
    private fun initPopUp() {
        val popUpView = layoutInflater.inflate(R.layout.menu_settings, null)
        // fixme-sw: init with dagger?
        settingsWindowDelegator = settingsPopupWindow.createPopupWindow(popUpView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        MenuSettingsBinding.bind(popUpView).apply {
            vm = settingsPopupViewModel
            listener = settingsWindowDelegator
        }
    }

    private fun bindContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = cashSummaryViewModel
        binding?.viewListener = this
    }

    companion object {
        private const val ADD_CASH_DIALOG = "addCashDialog"
    }
}