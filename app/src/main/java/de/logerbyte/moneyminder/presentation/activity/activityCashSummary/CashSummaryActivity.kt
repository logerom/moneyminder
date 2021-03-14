package de.logerbyte.moneyminder.presentation.activity.activityCashSummary

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import dagger.android.support.DaggerAppCompatActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.cashOverview.ViewListener
import de.logerbyte.moneyminder.presentation.dialog.dialogAddCash.AddCashDialogFragment
import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack
import de.logerbyte.moneyminder.presentation.cashadapter.DayExpenseViewModel
import de.logerbyte.moneyminder.presentation.cashadapter.DayExpenseViewModel.ActivityListener
import de.logerbyte.moneyminder.presentation.dialog.dialogEdit.EditDialogFragment
import de.logerbyte.moneyminder.cashOverview.menu.MenuVm
import de.logerbyte.moneyminder.cashOverview.menu.SettingsPopupWindow
import de.logerbyte.moneyminder.data.viewItem.CashViewItem
import de.logerbyte.moneyminder.databinding.ActivityMainBinding
import de.logerbyte.moneyminder.databinding.MenuSettingsBindingImpl
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class CashSummaryActivity : DaggerAppCompatActivity(), ActivityListener, ViewListener {
    private lateinit var settingsWindowDelegator: SettingsPopupWindow
    private var binding: ActivityMainBinding? = null

    @Inject
    @Named("ANDROID_VIEWMODEL")
    lateinit var cashSummaryViewModel: CashSummaryViewModel

    @Inject
    lateinit var menuVm: MenuVm

    @Inject
    lateinit var settingsPopupWindow: SettingsPopupWindow

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        bindView()
        initActionBar()
        initPopUp()
        initBinding()
    }

    private fun initBinding() {
        cashSummaryViewModel.cashItems.observe(this, Observer { setListAdapter(it)})
    }

    private fun setListAdapter(it: List<CashViewItem>?) {
        TODO("Not yet implemented")
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
        // fixme-sw: init with dagger?
        settingsWindowDelegator = settingsPopupWindow.createPopupWindow(popUpView, RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT, true)
        MenuSettingsBindingImpl.bind(popUpView).apply {
            vm = menuVm
            listener = settingsWindowDelegator
        }
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