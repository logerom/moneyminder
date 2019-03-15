package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.Application;
import android.databinding.ObservableField;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.util.DigitUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogViewModel implements CashEditDialog.Listener {

    private long entryId;
    private ObservableField<String> cashName = new ObservableField<>();
    private ObservableField<String> cashCategory = new ObservableField<>();
    private ObservableField<String> cashDate = new ObservableField<>();
    private ObservableField<String> cashInEuro = new ObservableField<>();

    private AppDatabaseManager appDatabaseManager;

    public void setViewInterface(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    ViewInterface viewInterface;

    public DialogViewModel(long entryId, String cashDate, String cashName, String cashInEuro, String cashCategory) {
        this.entryId = entryId;
        this.cashDate.set(cashDate);
        this.cashName.set(cashName);
        this.cashCategory.set(cashCategory);
        this.cashInEuro.set(cashInEuro);
    }

    @Override
    public void onEditClick(DialogViewModel item) {
        Expense expenseToUpdate = new Expense(item.entryId, item.cashName.get(), item.cashCategory.get(),
                item.cashDate.get(), Double.valueOf(DigitUtil.commaToDot(item.cashInEuro.get())));

    appDatabaseManager.updateCashItem(expenseToUpdate)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> viewInterface.onUpdateItem());
    }

    public void initAppDatabaseManager(Application application) {
        appDatabaseManager = new AppDatabaseManager(application);
    }

    public interface ViewInterface {

        void onItemDeleted();

        // FIXME: 21.09.18 delete interface
        void onUpdateItem();
    }

    public ObservableField<String> getCashName() {
        return cashName;
    }

    public ObservableField<String> getCashCategory() {
        return cashCategory;
    }

    public ObservableField<String> getCashDate() {
        return cashDate;
    }

    public ObservableField<String> getCashInEuro() {
        return cashInEuro;
    }
}
