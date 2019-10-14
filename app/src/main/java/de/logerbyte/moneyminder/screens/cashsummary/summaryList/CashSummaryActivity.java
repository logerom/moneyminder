package de.logerbyte.moneyminder.screens.cashsummary.summaryList;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.ActivityMainBinding;
import de.logerbyte.moneyminder.dialogs.editDialog.EditDialog;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface;

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
    public void showEditDialog(DayExpenseViewModel item,
            ViewInterface dialogVmListener) {
      //  new EditDialog().show(getSupportFragmentManager(), "Base_Dialog");
                EditDialog baseDialog = new EditDialog();
                baseDialog.show(getSupportFragmentManager(), "Edit_Dialog");
                // TODO: 2019-09-27 implement parcelable in bundle for item transaction between fragment
                baseDialog.setCash(item);
                baseDialog.setAdapterCallback(dialogVmListener);
    }
}
