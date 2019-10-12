package de.logerbyte.moneyminder.dialogs.editDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;

import org.jetbrains.annotations.NotNull;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.DialogBinding;
import de.logerbyte.moneyminder.dialogs.BaseDialog1;
import de.logerbyte.moneyminder.dialogs.BaseDialog1ViewModelListener;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.DayExpenseViewModel;
import de.logerbyte.moneyminder.screens.cashsummary.cashadapter.ViewInterface;
import de.logerbyte.moneyminder.viewModels.CashViewModel;

public class EditDialogJava extends BaseDialog1 implements BaseDialogListenerView {

    private DialogBinding binding;
    protected LayoutInflater inflater;
    private EditDialogViewModel editDialogViewModel;
    private ViewInterface dialogVmListener;
    private CashViewModel cashViewModel = new CashViewModel();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        bindViewModel(context);
    }

    protected void bindViewModel(Context context) {
        // TODO: 2019-10-08 include view programmatically into a container in dialog.xml
        editDialogViewModel = new EditDialogViewModel(((Activity) context).getApplication(), this);
        editDialogViewModel.setViewInterface(dialogVmListener);
        editDialogViewModel.setDialogViewListener(this);
        editDialogViewModel.setCashViewModel(cashViewModel);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        binding = DataBindingUtil.inflate(inflater, getLayout(), null, false);
        binding.setDialogVM(editDialogViewModel);
        binding.setCashVM(cashViewModel);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(binding.getRoot());
        return builder.create();
    }

    protected @LayoutRes
    int getLayout() {
        return R.layout.dialog;
    }

    // fixme: 31.08.18 transfer file to kotlin

    public void setCash(DayExpenseViewModel item) {
        cashViewModel.setCash(item);
    }

    // FIXME: 21.09.18 prevent pass throw callback from adapter to dialog viewmodel. Better callback from
    // model to model (layer to layer)

    public void setAdapterCallback(ViewInterface dialogVmListener) {
        this.dialogVmListener = dialogVmListener;
    }

    @Override
    public void dismissDialog() {
        dismiss();
    }

    @NotNull
    @Override
    public BaseDialog1ViewModelListener setViewModelListener() {
        return null;
    }

    @Override
    public void additionalBinding() {

    }
}
