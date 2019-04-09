package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.databinding.DialogEditBinding;

public class CashEditDialog extends DialogFragment {

    private DialogEditBinding binding;
    private Listener callback;
    private LayoutInflater inflater;
    private DialogViewModel vm;
    private DialogViewModel.ViewInterface dialogVmListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        vm.initAppDatabaseManager(getActivity().getApplication());
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit,null, false);

        binding.setVm(vm);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        builder.setPositiveButton("Edit", (dialog, which) -> callback.onEditClick(vm));
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    // fixme: 31.08.18 transfer file to kotlin

    public void bindViewModel(DayExpenseViewModel item) {
        vm = new DialogViewModel(
                item.getEntryId(), item.getCashDate().get(), item.getCashName().get(), item.getCashInEuro().get(),
                item.getCashCategory().get());
        callback = vm;
    }

    // FIXME: 21.09.18 prevent pass throw callback from adapter to dialog viewmodel. Better callback from
    // model to model (layer to layer)

    public void setAdapterCallback(DialogViewModel.ViewInterface dialogVmListener) {
        this.dialogVmListener = dialogVmListener;
        vm.setViewInterface(dialogVmListener);
    }

    public interface Listener{
        void onEditClick(DialogViewModel item);
    }
}
