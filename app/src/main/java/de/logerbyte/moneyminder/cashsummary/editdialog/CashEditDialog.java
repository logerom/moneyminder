package de.logerbyte.moneyminder.cashsummary.editdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.cashadapter.CashAdapterItemViewModel;
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;
//import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;

public class CashEditDialog extends DialogFragment {

    private AdapterEntryBinding binding;
    private CashAdapterItemViewModel cashAdapterItemViewModel;
    private Listener listener;
    private DialogViewModel dialogViewModel;
    private LayoutInflater inflater;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.adapter_entry,null, false);

        binding.setVmCashItem(cashAdapterItemViewModel);
        //        // FIXME: 17.09.18 doesnt need!?
//        dialogViewModel = ViewModelProviders.of(this).get(DialogViewModel.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        builder.setPositiveButton("Edit", (dialog, which) -> listener.onEditClick(
                cashAdapterItemViewModel));
        builder.setNegativeButton("Cancel", null);

        return builder.create();
    }

    // TODO: 31.08.18 transfer file to kotlin

    public void bindViewModel(CashAdapterItemViewModel item) {
        cashAdapterItemViewModel = item;
    }

    public interface Listener{
        void onEditClick(CashAdapterItemViewModel item);
    }
}
