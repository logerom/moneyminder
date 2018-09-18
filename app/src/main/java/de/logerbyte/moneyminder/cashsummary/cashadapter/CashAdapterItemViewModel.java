package de.logerbyte.moneyminder.cashsummary.cashadapter;

import android.databinding.ObservableField;
import android.view.View;

/**
 * Created by logerom on 29.07.18.
 */

public class CashAdapterItemViewModel {

    long entryId;
    ObservableField<String> cashName = new ObservableField<>();
    ObservableField<String> cashDate = new ObservableField<>();
    ObservableField<String> cashInEuro = new ObservableField<>();
    private AdapterListener cashAdapterItemAdapterListener;
    private ActivityListener mActivityListener;

    // FIXME: 17.09.18 no constructor for if we extend from AndroidViewModel
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
        // TODO: 14.09.18 create dialog from view model? Better to create view from view!?
          mActivityListener.onItemClicked(this);
    }

    public void setAdapterListener(AdapterListener cashAdapter) {
        cashAdapterItemAdapterListener = cashAdapter;
    }

    // TODO: 17.09.18 set activity listener in adapter view model
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
