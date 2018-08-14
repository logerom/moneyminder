package de.logerbyte.moneyminder.cashsummary;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.util.ConvertUtil;
import de.logerbyte.moneyminder.util.DigitUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by logerom on 28.07.18.
 */

public class CashSummaryViewModel extends ViewModel{
    private final CashAdapter cashAdapter;
    private final AppDatabaseManager appDatabaseManager;
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private ArrayList<CashItem> cashList = new ArrayList<>();
    private ObservableField<String> totalExpenses = new ObservableField<>();

    // TODO: 14.08.18 add live data in view and viewModel which updates the "view observable"

    public CashSummaryViewModel(CashSummaryActivity cashSummaryActivity) {
        appDatabaseManager = new AppDatabaseManager(cashSummaryActivity);
        cashAdapter = new CashAdapter();

        loadExpenseList();
        totalExpenses.set(String.valueOf(0));
    }

    private void loadExpenseList() {
        appDatabaseManager.getAllExpense()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(expenses -> {
                    cashList = ConvertUtil.expensesToCashItems(expenses);
                    addCashToTotal(expenses);

                    cashAdapter.setList(cashList);
                    cashAdapter.notifyDataSetChanged();
                });


    }

    public void onClickAddCash(View view){
        setCashItem();
        loadExpenseList();
    }

    private void addCashToTotal(List<Expense> cashList) {
        double totalCash = 0.0;

        for (Expense expense : cashList) {
            totalCash = totalCash + expense.cashInEuro;
        }

        totalExpenses.set(String.valueOf(Math.floor(totalCash * 100) / 100));
    }

    private void setCashItem() {
        CashItem cashItem = new CashItem(cashDate.get(), cashName.get(), cashInEuro.get());
        Expense expense = new Expense(null, cashName.get(), cashDate.get(), Double.valueOf(DigitUtil.commaToDot(cashInEuro.get())));
        cashList.add(cashItem);
        appDatabaseManager.insertCashItemIntoDB(expense)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                        .subscribe();
        // TODO: 14.08.18 what is when error?
    }


    @BindingAdapter({"adapter"})
    public static void setAdapter(RecyclerView recyclerView, CashAdapter cashAdapter) {
        recyclerView.setAdapter(cashAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
    }

    public CashAdapter getCashAdapter() {
        return cashAdapter;
    }

    public ObservableField<String> getCashName() {
        return cashName;
    }

    public ObservableField<String> getCashInEuro() {
        return cashInEuro;
    }

    public ObservableField<String> getCashDate() {
        return cashDate;
    }

    public ObservableField<String> getTotalExpenses() {
        totalExpenses.set(DigitUtil.dotToComma(totalExpenses.get()));
        return totalExpenses;
    }
}

