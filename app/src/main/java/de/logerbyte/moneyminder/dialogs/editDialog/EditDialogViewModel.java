package de.logerbyte.moneyminder.dialogs.editDialog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface;
import de.logerbyte.moneyminder.util.DigitUtil;
import de.logerbyte.moneyminder.viewModels.CashViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EditDialogViewModel extends AndroidViewModel implements BseDialogListenerViewModel {

    private final BaseDialogListenerView callback;
    private AppDatabaseManager appDatabaseManager;
    CashViewModel cashViewModel;
    ViewInterface viewInterface;
    private BaseDialogListenerView baseDialogListenerView;

    public EditDialogViewModel(Application application, BaseDialogListenerView listener) {
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

        baseDialogListenerView.dismissDialog();

        appDatabaseManager.updateCashItem(expenseToUpdate).subscribeOn(
                Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                aBoolean -> viewInterface.onUpdateItem());
    }

    @Override
    public void editCashCancel() {
        callback.dismissDialog();
    }

    public void setDialogViewListener(BaseDialogListenerView baseDialogListenerView) {
        this.baseDialogListenerView = baseDialogListenerView;
    }

}
