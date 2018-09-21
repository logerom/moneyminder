package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.Application;
import android.databinding.ObservableField;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogViewModel implements CashEditDialog.Listener {

    private long entryId;
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();

    private AppDatabaseManager appDatabaseManager;
    ViewInterface viewInterface;

    public DialogViewModel(long entryId, String cashDate, String cashName, String cashInEuro) {
        this.entryId = entryId;
        this.cashDate.set(cashDate);
        this.cashName.set(cashName);
        this.cashInEuro.set(cashInEuro);
    }

    @Override
    public void onEditClick(DialogViewModel item) {
        Expense expenseToUpdate = new Expense(item.entryId, item.cashName.get(), item.cashDate.get(),
                Double.valueOf(item.cashInEuro.get()));

        // TODO: 21.09.18 update and reload adapter: is there an automatic reload mechanism for adapter when
        // db entry changes?

        appDatabaseManager.updateCashItem(expenseToUpdate)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> viewInterface.onItemDeleted());
    }

    public void initAppDatabaseManager(Application application) {
        appDatabaseManager = new AppDatabaseManager(application);
    }

    public interface ViewInterface {

        void onItemDeleted();
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
