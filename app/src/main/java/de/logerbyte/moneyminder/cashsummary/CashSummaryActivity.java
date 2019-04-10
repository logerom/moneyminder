package de.logerbyte.moneyminder.cashsummary;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.cashsummary.editdialog.CashEditDialog;
import de.logerbyte.moneyminder.cashsummary.editdialog.DialogViewModel;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;

public class CashSummaryActivity extends FragmentActivity implements DayExpenseViewModel.ActivityListener {

    private CashSummaryViewModel cashSummaryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindViewModel();
        bindView();
        setActionBar(findViewById(R.id.my_toolbar));
    }

    private void bindViewModel() {
        cashSummaryViewModel = ViewModelProviders.of(this).get(CashSummaryViewModel.class);
        cashSummaryViewModel.setCashSummaryActivity(this);
    }

    private void bindView() {
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(cashSummaryViewModel);
    }

    @Override
    public void showEditDialog(DayExpenseViewModel item, DialogViewModel.ViewInterface dialogVmListener) {
        CashEditDialog cashEditDialog = new CashEditDialog();
        cashEditDialog.bindViewModel(item);
        cashEditDialog.setAdapterCallback(dialogVmListener);

        cashEditDialog.show(getSupportFragmentManager(), "Edit_Dialog");
    }
}
