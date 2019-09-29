package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.view.View;

import org.jetbrains.annotations.NotNull;

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

    public DialogViewModel(Application application, DialogListenerView listener) {
        super(application);
        appDatabaseManager = new AppDatabaseManager(application.getBaseContext());
        callback = listener;
    }

    public void setViewInterface(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    @Override
    public void onClickOk(@NotNull View view) {
        // TODO: 2019-09-29  cashviemodel is not initialized. Get it over xml or from class
        Expense expenseToUpdate = new Expense(cashViewModel.getEntryId(),
                cashViewModel.getCashName().get(), cashViewModel.getCashCategory(),
                cashViewModel.getCashDate().get(),
                Double.valueOf(DigitUtil.commaToDot(cashViewModel.getCashAmount().get())));

        appDatabaseManager.updateCashItem(expenseToUpdate)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> viewInterface.onUpdateItem());
    }

    @Override
    public void onClickCancel(@NotNull View view) {
        callback.onClickCancel(view);
    }

    public interface ViewInterface {

        void onItemDeleted();

        // FIXME: 21.09.18 delete interface
        void onUpdateItem();
    }
}
