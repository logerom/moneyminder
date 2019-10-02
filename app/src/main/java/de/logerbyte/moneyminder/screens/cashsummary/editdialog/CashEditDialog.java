package de.logerbyte.moneyminder.screens.cashsummary.editdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.DialogBinding;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.viewModels.CashViewModel;

public class CashEditDialog extends DialogFragment implements DialogListenerView {

    private DialogBinding binding;
    private LayoutInflater inflater;
    private DialogViewModel dialogViewModel;
    private DialogViewModel.ViewInterface dialogVmListener;
    private CashViewModel cashViewModel = new CashViewModel();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bindViewModel(context);
    }

    private void bindViewModel(Context context) {
        dialogViewModel = new DialogViewModel(((Activity) context).getApplication(), this);
        dialogViewModel.setViewInterface(dialogVmListener);
        dialogViewModel.setDialogViewListener(this);
        dialogViewModel.setCashViewModel(cashViewModel);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog, null, false);
        binding.setDialogVM(dialogViewModel);
        binding.setCashVM(cashViewModel);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        return builder.create();
    }

    // fixme: 31.08.18 transfer file to kotlin

    public void setCash(DayExpenseViewModel item) {
        cashViewModel.setCash(item);
    }

    // FIXME: 21.09.18 prevent pass throw callback from adapter to dialog viewmodel. Better callback from
    // model to model (layer to layer)

    public void setAdapterCallback(DialogViewModel.ViewInterface dialogVmListener) {
        this.dialogVmListener = dialogVmListener;
    }

    public void dismissDialog() {
        dismiss();
    }

    @Override
    public void onClickCancel(View view) {
        dismissDialog();
    }

    @Override
    public void onClickOk(@NotNull View view) {
        dismissDialog();
    }
}
