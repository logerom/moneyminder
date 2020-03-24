package de.logerbyte.moneyminder.screens.cashsummary.summaryList;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.addCashDialog.AddCashDialogFragment;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;
import de.logerbyte.moneyminder.editDialog.EditDialogFragment;
import de.logerbyte.moneyminder.screens.cashsummary.ViewListener;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.AdapterCallBack;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;

public class CashSummaryActivity extends FragmentActivity implements
        DayExpenseViewModel.ActivityListener, ViewListener {

    private static final String ADD_CASH_DIALOG = "addCashDialog";
    private CashSummaryViewModel cashSummaryViewModel;
    private ActivityMainBinding binding;

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewModel(cashSummaryViewModel);
        binding.setViewListener(this);
    }

    @Override
    public void showEditDialog(DayExpenseViewModel item, AdapterCallBack dialogVmListener) {
        //  new EditDialogFragment().show(getSupportFragmentManager(), "Base_Dialog");
        EditDialogFragment baseDialog = new EditDialogFragment();
        baseDialog.show(getSupportFragmentManager(), "Edit_Dialog");
        // TODO: 2019-09-27 implement parcelable in bundle for item transaction between fragment
        baseDialog.setCash(item);
        baseDialog.setAdapterCallback(dialogVmListener);
    }

    @Override
    public void onCLickFab() {
        AdapterCallBack adapterCallBack = (AdapterCallBack) binding.rvCosts.getAdapter();
        AddCashDialogFragment cashDialog = new AddCashDialogFragment();
        cashDialog.setAdapterCallback(adapterCallBack);
        cashDialog.show(getSupportFragmentManager(), ADD_CASH_DIALOG);
    }
}
