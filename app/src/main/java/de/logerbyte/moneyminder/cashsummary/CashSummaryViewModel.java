package de.logerbyte.moneyminder.cashsummary;

import android.arch.lifecycle.ViewModel;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by logerom on 28.07.18.
 */

public class CashSummaryViewModel extends ViewModel{
    private final CashAdapter cashAdapter;
    private String date;
    private String cashName;
    private String cashInEuro;

    public CashSummaryViewModel() {
        cashAdapter = new CashAdapter();
    }

    public void onClickAddCash(View view){

    }

    @BindingAdapter({"binding:adapter"})
    public static void setAdapter(RecyclerView recyclerView, CashAdapter cashAdapter) {
        recyclerView.setAdapter(cashAdapter);
    }

    public CashAdapter getCashAdapter() {
        return cashAdapter;
    }
}
