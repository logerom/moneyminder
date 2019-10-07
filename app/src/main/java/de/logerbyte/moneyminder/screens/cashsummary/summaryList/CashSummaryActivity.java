package de.logerbyte.moneyminder.screens.cashsummary.summaryList;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;
import de.logerbyte.moneyminder.dialogs.editDialog.CashEditDialog;
import de.logerbyte.moneyminder.dialogs.editDialog.DialogViewModel;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;

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
        cashEditDialog.show(getSupportFragmentManager(), "Edit_Dialog");
        // TODO: 2019-09-27 implement parcelable in bundle for item transaction between fragment
        cashEditDialog.setCash(item);
        cashEditDialog.setAdapterCallback(dialogVmListener);
    }
}
