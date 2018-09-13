package de.logerbyte.moneyminder.cashsummary;

import android.databinding.ObservableField;

/**
 * Created by logerom on 29.07.18.
 */

public class CashAdapterItem {

    long entryId;
    ObservableField<String> cashName = new ObservableField<>();
    ObservableField<String> cashDate = new ObservableField<>();
    ObservableField<String> cashInEuro = new ObservableField<>();
    private Listener cashAdapterItemListener;

    public CashAdapterItem(long entryId, String cashDate, String cashName, String cashInEuro) {
        this.entryId = entryId;
        this.cashDate.set(cashDate);
        this.cashName.set(cashName);
        this.cashInEuro.set(cashInEuro);
    }

    public long getEntryId() {
        return entryId;
    }

    public ObservableField<String> getCashName() {
        return cashName;
    }

    public ObservableField<String> getCashDate() {
        return cashDate;
    }

    public ObservableField<String> getCashInEuro() {
        return cashInEuro;
    }

    public void onCashItemDeleteClicked(Long cashItemId) {
        cashAdapterItemListener.onItemDeleteClicked(cashItemId);
    }

    public void setListener(Listener cashAdapter) {
        cashAdapterItemListener = cashAdapter;
    }

    interface Listener {
        void onItemDeleteClicked(Long cashItemId);
    }


}
