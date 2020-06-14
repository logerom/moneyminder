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
    private ObservableField<Double> totalExpenses = new ObservableField<>();
    private ObservableField<String> totalBudget = new ObservableField<>("0,00");
    private ObservableField<Double> totalSaving = new ObservableField<>();


    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"

    public CashSummaryViewModel(@NonNull Application application) {
        super(application);
        cashAdapter = new CashAdapter(new AppDatabaseManager(application));
        cashAdapter.setLisener(this);
        totalExpenses.set(0d);
    }

    private void addCashToTotal(List<Expense> cashList) {
        totalExpenses.set(DigitUtil.INSTANCE.getCashTotal(cashList));
    }

    public ObservableField<Double> getTotalExpenses() {
        return totalExpenses;
    }

    public ObservableField<String> getTotalBudget() {
        totalBudget.set(DigitUtil.INSTANCE.dotToComma(totalBudget.get()));
        return totalBudget;
    }

    public ObservableField<Double> getTotalSaving() {
        totalSaving.set(totalSaving.get());
        return totalSaving;
    }

    @Override
    public void onLoadedExpenses(@NotNull List<? extends Expense> expenses, int allBudget) {
        addCashToTotal((List) expenses);
        totalBudget.set(DigitUtil.INSTANCE.doubleToString(allBudget));
        totalSaving.set(Double.valueOf(allBudget) - totalExpenses.get());

    }

    void setCashSummaryActivity(DayExpenseViewModel.ActivityListener cashSummaryActivity) {
        cashAdapter.setActivityListener(cashSummaryActivity);
    }

    public CashAdapter getCashAdapter() {
        return cashAdapter;
    }
}

