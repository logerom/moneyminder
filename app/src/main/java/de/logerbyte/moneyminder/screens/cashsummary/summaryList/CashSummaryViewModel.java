package de.logerbyte.moneyminder.screens.cashsummary.summaryList;

import android.app.Application;
import android.text.Editable;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.logerbyte.moneyminder.ErrorHandling;
import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.CashAdapter;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.util.ConvertUtil;
import de.logerbyte.moneyminder.util.DigitUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by logerom on 28.07.18.
 */

public class CashSummaryViewModel extends AndroidViewModel implements CashAdapter.Listener {

    private final CashAdapter cashAdapter;
    private final AppDatabaseManager appDatabaseManager;
    // fixme: 15.08.18 observable from cashItem instead every own field?
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashCategory = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private ArrayList<DayExpenseViewModel> cashList = new ArrayList<>();
    private ObservableField<String> totalExpenses = new ObservableField<>();
    private ObservableInt cashCategorySelectedItem = new ObservableInt();
    private ArrayList<String> cashCategoryList = new ArrayList<>();

    public boolean dotDelete;
    String newText = new String();
    private DayExpenseViewModel.ActivityListener cashSummaryActivity;
    private boolean isNewSpinnerValue = false;
    private int newSpinnerIndex;
    private String newSCategoryName;

    // fixme: 14.08.18 add live data in view and viewModel which updates the "view observable"

    public CashSummaryViewModel(@NonNull Application application) {
        super(application);
        appDatabaseManager = new AppDatabaseManager(application);
        cashAdapter = new CashAdapter(appDatabaseManager);
        cashAdapter.setLisener(this);
        totalExpenses.set(String.format("%.2f", 0f));
        initList();
        setActualDate();
    }

    private void initList() {
        cashCategoryList.add("Essen");
        cashCategoryList.add("Sonstiges");
        cashCategoryList.add("Beauty");
    }

    private void setActualDate() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yy");
        cashDate.set(df.format(date));
    }

    // FIXME: 13.09.18 source out loadExpesne from adapter and viewModel in base class / USE CASE
    private void loadExpenseList() {
        appDatabaseManager.getAllExpense().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(expenses -> {
            cashList = ConvertUtil.expensesToCashItems(expenses);
            addCashToTotal(expenses);
            cashAdapter.initList(expenses);
        });
    }

    // FIXME: 11.04.19 handle with binding adapter? didnt want view context in class
    // TODO: 20.09.2019 switch into another viewmodel
    public void onClickAddCash(View view) {
        if (isInputCorrect()) {
            ErrorHandling.Companion.showToast(view.getContext(), "Input should not be null");
            return;
        }

        Double date = DigitUtil.commaToDot(cashInEuro.get()) == "" ? 0.00 : Double.valueOf(DigitUtil.commaToDot(cashInEuro.get()));

        Expense expense = new Expense(null, cashName.get(), newSCategoryName, cashDate.get(), date);
        appDatabaseManager.insertCashItemIntoDB(expense).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> loadExpenseList());
        // fixme: 14.08.18 what is when error?

        clearInputField();
    }

    public boolean isInputCorrect() {
        return !areInputFieldsNotNull();
    }

    public boolean areInputFieldsNotNull() {
        return cashDate.get() != null && cashName.get() != null &&
                cashInEuro.get() != null;
    }

    public void onTextChanged(Editable s) {
        if (dotDelete) {
            dotDelete = false;
            return;
        }
        // fixme: 02.09.18 make util class with that dot delete logic

        if (s.length() < newText.length()) {
            char charAt = newText.toString().charAt(newText.toString().length() - 1);
            if (charAt == '.') {
                dotDelete = true;
                s.delete(s.toString().length() - 1, s.toString().length());
            }
            newText = s.toString();
            return;
        }

        if (s.toString().length() == 2 || s.toString().length() == 5) {
            s.append(".");
            newText = s.toString();
            return;
        }

        newText = s.toString();
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

    public ObservableInt getCashCategorySelectedItem() {

        if (isNewSpinnerValue) {
            newSpinnerIndex = cashCategorySelectedItem.get();
            newSCategoryName = cashCategoryList.get(newSpinnerIndex);
            isNewSpinnerValue = false;
        } else {
            isNewSpinnerValue = true;
        }

        return cashCategorySelectedItem;
    }

    public ArrayList<String> getCashCategoryList() {
        return cashCategoryList;
    }

    @Override
    public void onLoadedExpenses(@NotNull List<? extends Expense> expenses) {
        addCashToTotal((List) expenses);
    }

    public void setCashSummaryActivity(DayExpenseViewModel.ActivityListener cashSummaryActivity) {
        this.cashSummaryActivity = cashSummaryActivity;
        cashAdapter.setActivityListener(cashSummaryActivity);
    }
}

