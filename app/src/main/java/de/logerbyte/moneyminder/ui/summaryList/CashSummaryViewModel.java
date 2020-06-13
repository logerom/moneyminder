package de.logerbyte.moneyminder.ui.summaryList;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.util.DigitUtil;

/**
 * Created by logerom on 28.07.18.
 */

public class CashSummaryViewModel extends AndroidViewModel implements CashAdapter.Listener {

    CashAdapter cashAdapter;
    private ObservableField<String> totalExpenses = new ObservableField<>();


    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"

    public CashSummaryViewModel(@NonNull Application application) {
        super(application);
        cashAdapter = new CashAdapter(new AppDatabaseManager(application));
        cashAdapter.setLisener(this);
        totalExpenses.set(String.format("%.2f", 0f));
    }

    private void addCashToTotal(List<Expense> cashList) {
        totalExpenses.set(DigitUtil.getCashTotal(cashList));
    }

    public ObservableField<String> getTotalExpenses() {
        totalExpenses.set(DigitUtil.dotToComma(totalExpenses.get()));
        return totalExpenses;
    }

    @Override
    public void onLoadedExpenses(@NotNull List<? extends Expense> expenses, int allBudget) {
        // TODO-SW: add budgets to all summary
        addCashToTotal((List) expenses);
    }

    void setCashSummaryActivity(DayExpenseViewModel.ActivityListener cashSummaryActivity) {
        cashAdapter.setActivityListener(cashSummaryActivity);
    }

    public CashAdapter getCashAdapter() {
        return cashAdapter;
    }
}

