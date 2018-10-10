package de.logerbyte.moneyminder.cashsummary

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import de.logerbyte.moneyminder.R
import de.logerbyte.moneyminder.cashsummary.cashadapter.CashAdapterItemViewModel
import de.logerbyte.moneyminder.cashsummary.editdialog.CashEditDialog
import de.logerbyte.moneyminder.cashsummary.editdialog.DialogViewModel
import de.logerbyte.moneyminder.databinding.ActivityMainBinding

class CashSummaryActivity : FragmentActivity(), CashAdapterItemViewModel.ActivityListener {

    private var cashSummaryViewModel: CashSummaryViewModel? = null
    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindViewModel()
        bindView()

        //  setActionBar(binding.tool_bar);
    }

    private fun bindViewModel() {
        cashSummaryViewModel = ViewModelProviders.of(this).get(CashSummaryViewModel::class.java)
        cashSummaryViewModel!!.setCashSummaryActivity(this)
    }

    private fun bindView() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.viewModel = cashSummaryViewModel
    }

    override fun onItemClicked(item: CashAdapterItemViewModel, dialogVmListener: DialogViewModel.ViewInterface) {
        val cashEditDialog = CashEditDialog()
        cashEditDialog.bindViewModel(item)
        cashEditDialog.setAdapterCallback(dialogVmListener)

        cashEditDialog.show(supportFragmentManager, "Edit_Dialog")
    }
}
