package de.logerbyte.moneyminder.screens.cashsummary.editdialog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.util.DigitUtil;
import de.logerbyte.moneyminder.viewModels.CashViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogViewModel extends AndroidViewModel implements DialogListener {

    private final DialogListenerView callback;
    private AppDatabaseManager appDatabaseManager;
    CashViewModel cashViewModel;
    ViewInterface viewInterface;
    private DialogListenerView dialogListenerView;

    public DialogViewModel(Application application, DialogListenerView listener) {
        super(application);
        appDatabaseManager = new AppDatabaseManager(application.getBaseContext());
        callback = listener;
    }

    public void setViewInterface(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public void setCashViewModel(CashViewModel cashViewModel) {
        this.cashViewModel = cashViewModel;
    }

    @Override
    public void editCash() {
        Expense expenseToUpdate = new Expense(cashViewModel.getEntryId(),
                cashViewModel.getCashName().get(), cashViewModel.getCashCategory(),
                cashViewModel.getCashDate().get(),
                Double.valueOf(DigitUtil.commaToDot(cashViewModel.getCashAmount().get())));

        dialogListenerView.dismissDialog();

        appDatabaseManager.updateCashItem(expenseToUpdate).subscribeOn(
                Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                aBoolean -> viewInterface.onUpdateItem());
    }

    @Override
    public void editCashCancel() {
        callback.dismissDialog();
    }

    public void setDialogViewListener(DialogListenerView dialogListenerView) {
        this.dialogListenerView = dialogListenerView;
    }

    public interface ViewInterface {

        void onItemDeleted();

        // TODO: 2019-09-30 check listener
        void onUpdateItem();
    }
}
