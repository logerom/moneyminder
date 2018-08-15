package de.logerbyte.moneyminder.cashsummary;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;

/**
 * Created by logerom on 28.07.18.
 */

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.ViewHolder> {


    private ArrayList<CashItem> list = new ArrayList<>();
    private LayoutInflater layoutInflater;
    CashSummaryMVVM.Listener cashViewModelListener;

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
        CashItem cashItem = list.get(position);
        holder.binding.setVmCashItem(cashItem);
        holder.binding.setVmCashSummary(cashViewModelListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
        // TODO: 14.08.18 will be called every time I switched in date - Enntry - CashInEuro
    }

    public void setList(ArrayList<CashItem> list) {
        this.list = list;
    }

    public void setCashViewModelListener(CashSummaryMVVM.Listener cashViewModelListener) { this.cashViewModelListener = cashViewModelListener; }

    // fixme: 29.07.18 why an extra viewHolder?
    protected static class ViewHolder extends RecyclerView.ViewHolder {

        AdapterEntryBinding binding;

        public ViewHolder(AdapterEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
