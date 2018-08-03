package de.logerbyte.moneyminder.cashsummary;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by logerom on 28.07.18.
 */

public class CashSummaryViewModel extends ViewModel{
    private final CashAdapter cashAdapter;
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private ArrayList<CashItem> cashList = new ArrayList<>();
    private ObservableField<String> totalExpenses = new ObservableField<>();

    public CashSummaryViewModel() {
        // TODO: 30.07.18 load cash list from database
        cashAdapter = new CashAdapter();
        totalExpenses.set(String.valueOf(0));
    }

    public void onClickAddCash(View view){
        // TODO: 29.07.18 add item to list + notify data set changed
        setCashItem();
        addCashToTotal();
        cashAdapter.setList(cashList);
        cashAdapter.notifyDataSetChanged();
    }

    private void addCashToTotal() {
        Integer euro = Integer.valueOf(totalExpenses.get()) + Integer.valueOf(cashInEuro.get());
        totalExpenses.set(String.valueOf(euro));
    }

    private void setCashItem() {
        cashList.add(new CashItem(cashDate.get(), cashName.get(), cashInEuro.get()));
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

    public ObservableField<String> getTotalExpenses() { return totalExpenses; }
}

