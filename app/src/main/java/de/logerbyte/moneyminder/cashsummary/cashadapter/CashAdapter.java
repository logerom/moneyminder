package de.logerbyte.moneyminder.cashsummary.cashadapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.cashsummary.editdialog.DialogViewModel;
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;
import de.logerbyte.moneyminder.db.AppDatabaseManager;
import de.logerbyte.moneyminder.db.expense.Expense;
import de.logerbyte.moneyminder.util.ConvertUtil;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by logerom on 28.07.18.
 */

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.ViewHolder> implements
        CashAdapterItemViewModel.AdapterListener, DialogViewModel.ViewInterface {

    private ArrayList<CashAdapterItemViewModel> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private AppDatabaseManager appDatabaseManager;
    private Listener mAdapterListener;
    private CashAdapterItemViewModel.ActivityListener cashSummaryActivity;

    public CashAdapter(AppDatabaseManager appDatabaseManager) {
        this.appDatabaseManager = appDatabaseManager;
        loadExpenseList();
    }

    // FIXME: 23.09.18 2 same loadExpense functions. base it.
    public void loadExpenseList() {
        appDatabaseManager.getAllExpense()
                .subscribeOn(Schedulers.io())
                .map(expenses -> sortExpenses(expenses))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(expenses -> {
                    list = ConvertUtil.expensesToCashItems(expenses);
                    mAdapterListener.onLoadedExpenses(expenses);
                    notifyDataSetChanged();
                });
    }

    private List<Expense> sortExpenses(List<Expense> expenses) {
        // FIXME: 22.09.18 sort as util class
        Collections.sort(expenses, (o1, o2) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy");
            Date d1 = null, d2 = null;

            try{
                d1 = sdf.parse(o1.cashDate);
                d2 = sdf.parse(o2.cashDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return d1.compareTo(d2);
        });
        Collections.reverse(expenses);
        return expenses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        AdapterEntryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.adapter_entry, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CashAdapterItemViewModel cashAdapterItemViewModel = list.get(position);
        cashAdapterItemViewModel.setAdapterListener(this);
        cashAdapterItemViewModel.setDialogViewModelListener(this);
        cashAdapterItemViewModel.setActivityListener(cashSummaryActivity);
        holder.binding.setVmCashItem(cashAdapterItemViewModel);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<CashAdapterItemViewModel> list) {
        this.list = list;
    }

    public void setLisener(CashAdapter.Listener adapterListener) {
        mAdapterListener = adapterListener;
    }

    @Override
    public void onItemDeleteClicked(Long cashItemId) {
        appDatabaseManager.deleteCashItem(cashItemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> loadExpenseList());
    }

    @Override
    public void onItemDeleted() {
        loadExpenseList();
    }

    @Override
    public void onUpdateItem() {
        loadExpenseList();
    }

    public void setActivityListener(CashAdapterItemViewModel.ActivityListener cashSummaryActivity) {
        this.cashSummaryActivity = cashSummaryActivity;
    }

    public interface Listener {
        void onLoadedExpenses(List<Expense> expenses);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterEntryBinding binding;

        public ViewHolder(AdapterEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
