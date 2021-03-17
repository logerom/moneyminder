package de.logerbyte.moneyminder.data.viewItem;

import android.view.View;

import androidx.databinding.ObservableField;

import de.logerbyte.moneyminder.presentation.cashadapter.AdapterCallBack;
import kotlin.Deprecated;

/**
 * Created by logerom on 29.07.18.
 */
@Deprecated(message = "Use CashViewItem")
public class DayExpenseViewViewItem implements ExpenseListViewItem {

    private long entryId;
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashCategory = new ObservableField<>();
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();
    private AdapterListener cashAdapterItemAdapterListener;
    private ActivityListener mActivityListener;
    private AdapterCallBack dialogVmListener;

    // FIXME: 17.09.18 only default constructor if we extend from AndroidViewModel. Use factory
    public DayExpenseViewViewItem(long entryId, String cashDate, String cashName, String cashCategory,
                                  String cashInEuro) {
        this.entryId = entryId;
        this.cashDate.set(cashDate);
        this.cashName.set(cashName);
        this.cashCategory.set(cashCategory);
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
    public ObservableField<String> getCashCategory() {
        return cashCategory;
    }

    public void onCashItemDeleteClicked(Long cashItemId) {
        cashAdapterItemAdapterListener.onItemDeleteClicked(cashItemId);
    }

    public void onCashItemClicked(View view) {
//        mActivityListener.showEditDialog(this, dialogVmListener);
    }

    public void setItemListener(AdapterListener cashAdapter) {
        cashAdapterItemAdapterListener = cashAdapter;
    }

    public void setActivityListener(ActivityListener activityListener) {
        mActivityListener = activityListener;
    }

    public void setDialogListener(AdapterCallBack dialogVmListener) {
        this.dialogVmListener = dialogVmListener;
    }

    interface AdapterListener {
        void onItemDeleteClicked(Long cashItemId);
    }

    public interface ActivityListener {

        void showEditDialog(DayExpenseViewItem item, AdapterCallBack dialogVmListener);
    }


}
