package de.logerbyte.moneyminder.cashsummary.cashadapter;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created by logerom on 29.07.18.
 */

public class CashAdapterItemViewModel {

    private long entryId;
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private AdapterListener cashAdapterItemAdapterListener;
    private ActivityListener mActivityListener;

    // FIXME: 17.09.18 only default constructor if we extend from AndroidViewModel. Use factory
    public CashAdapterItemViewModel(long entryId, String cashDate, String cashName, String cashInEuro) {
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
        cashAdapterItemAdapterListener.onItemDeleteClicked(cashItemId);
    }

    public void onCashItemClicked(View view) {
          mActivityListener.onItemClicked(this);
    }

    public void setAdapterListener(AdapterListener cashAdapter) {
        cashAdapterItemAdapterListener = cashAdapter;
    }

    public void setActivityListener(ActivityListener activityListener) {
        mActivityListener = activityListener;
    }

    interface AdapterListener {
        void onItemDeleteClicked(Long cashItemId);
    }

    public interface ActivityListener {
        void onItemClicked(CashAdapterItemViewModel item);
    }


}
