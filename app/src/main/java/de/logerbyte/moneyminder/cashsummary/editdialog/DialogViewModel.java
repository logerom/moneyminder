package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import de.logerbyte.moneyminder.cashsummary.cashadapter.CashAdapterItemViewModel;
import de.logerbyte.moneyminder.db.AppDatabaseManager;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DialogViewModel extends AndroidViewModel implements CashEditDialog.Listener {

    private final AppDatabaseManager appDatabaseManager;

    ViewInterface viewInterface;

    public DialogViewModel(@NonNull Application application) {
        super(application);
        appDatabaseManager = new AppDatabaseManager(application);

    }

    @Override
    public void onEditClick(CashAdapterItemViewModel item) {
        appDatabaseManager.deleteCashItem(item.getEntryId())
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> viewInterface.onItemDeleted());
    }

    // TODO: 18.09.18 set listener in dialog fragment + ViewModel or Presenter? No View interaction in class
    public void setListener(ViewInterface viewInterface) {
        this.viewInterface = viewInterface;
    }

    public interface ViewInterface {
        void onItemDeleted();
    }
}
