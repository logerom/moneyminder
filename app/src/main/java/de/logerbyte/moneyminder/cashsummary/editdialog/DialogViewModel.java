package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.Application;
import android.databinding.ObservableField;

import de.logerbyte.moneyminder.db.AppDatabaseManager;

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
        // TODO: 18.09.18 update item in db
//        appDatabaseManager.deleteCashItem(item.getEntryId())
//                .subscribeOn(Schedulers.computation())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(aBoolean -> viewInterface.onItemDeleted());
    }

    // TODO: 18.09.18 set listener in dialog fragment?

    public void setListener(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
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
