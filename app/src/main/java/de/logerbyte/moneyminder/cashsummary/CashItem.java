package de.logerbyte.moneyminder.cashsummary;

import android.databinding.ObservableField;

/**
 * Created by logerom on 29.07.18.
 */

public class CashItem {
    long entryId;
    ObservableField<String> cashName = new ObservableField<>();
    ObservableField<String> cashDate = new ObservableField<>();
    ObservableField<String> cashInEuro = new ObservableField<>();

    public CashItem(String cashDate, String cashName, String cashInEuro) {
        this.cashDate.set(cashDate);
        this.cashName.set(cashName);
        this.cashInEuro.set(cashInEuro);
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
}
