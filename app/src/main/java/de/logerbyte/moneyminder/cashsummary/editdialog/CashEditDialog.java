package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.cashadapter.CashAdapterItemViewModel;
import de.logerbyte.moneyminder.databinding.DialogEditBinding;

public class CashEditDialog extends DialogFragment {

    private DialogEditBinding binding;
    private Listener listener;
    private LayoutInflater inflater;
    private DialogViewModel vm;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        vm.initAppDatabaseManager(getActivity().getApplication());
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit,null, false);

        binding.setVmCashItem(vm);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        builder.setPositiveButton("Edit", (dialog, which) -> listener.onEditClick(vm));
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    // TODO: 31.08.18 transfer file to kotlin

    public void bindViewModel(CashAdapterItemViewModel item) {
        vm = new DialogViewModel(
                item.getEntryId(), item.getCashDate().get(),
                item.getCashName().get(), item.getCashInEuro().get());
        setListener(vm);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener{
        void onEditClick(DialogViewModel item);
    }
}
