package de.logerbyte.moneyminder.cashsummary;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
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

public class CashSummaryViewModel extends AndroidViewModel implements CashSummaryMVVM.Listener{
    private final CashAdapter cashAdapter = new CashAdapter();
    private final AppDatabaseManager appDatabaseManager;
    // fixme: 15.08.18 observable from cashItem instead every own field?
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private ArrayList<CashAdapterItem> cashList = new ArrayList<>();
    private ObservableField<String> totalExpenses = new ObservableField<>();

    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"

    public CashSummaryViewModel(@NonNull Application application) {
        super(application);
        appDatabaseManager = new AppDatabaseManager(application);
        cashAdapter.setCashViewModelListener(this);
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
        Expense expense = new Expense(null, cashName.get(), cashDate.get(), Double.valueOf(DigitUtil.commaToDot(cashInEuro.get())));
        appDatabaseManager.insertCashItemIntoDB(expense)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        aBoolean -> loadExpenseList());
        // fixme: 14.08.18 what is when error?

        clearInputField();
    }

    public void onTextChanged(CharSequence s, int start, int before, int count){
        boolean isDot = s.toString().contains(".");
//        boolean isDotAtThird = count ==
//        s.charAt(count) = "";
//        if(
    }

    public void afterTextChanged(Editable s){
        String string = s.toString();
        int stringLenght = string.length();
        if (stringLenght == 2 || stringLenght == 5) {
            String cashWithDot = cashDate.get().concat(".");
            cashDate.set(cashWithDot);
        }

        if (stringLenght == 8) {
            cashDate.set(string);
        }
// TODO: 22.08.18 the third and fith char shuld be automatically a dot
        // TODO: 23.08.18  ERROR: chars with soft input differ from string set in variable.
        // - after set dot the cursor goes at the beginning of the edittext and and the last char.

        // TODO: 23.08.18 https://gist.github.com/hidroh/77ca470bbb8b5b556901

        /*        new Character("c");

        if(string)

        cashDate.set("99");
        */
    }


    private void clearInputField() {
        cashDate.set("");
        cashName.set("");
        cashInEuro.set("");
    }

    private void addCashToTotal(List<Expense> cashList) {
        totalExpenses.set(DigitUtil.getCashTotal(cashList));
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

    @Override
    public void onCashItemDeleteClicked(Long cashItemId) {
        appDatabaseManager.deleteCashItem(cashItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {
                   loadExpenseList();
                });
    }
}

