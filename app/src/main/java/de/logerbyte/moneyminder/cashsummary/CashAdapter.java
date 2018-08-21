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


    private ArrayList<CashAdapterItem> list = new ArrayList<>();
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
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CashAdapterItem cashAdapterItem = list.get(position);
        holder.binding.setVmCashItem(cashAdapterItem);

        // TODO: 20.08.18 for callback in cashAdapterItem?
        holder.binding.setVmCashSummary(cashViewModelListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<CashAdapterItem> list) {
        this.list = list;
    }

    public void setCashViewModelListener(CashSummaryMVVM.Listener cashViewModelListener) { this.cashViewModelListener = cashViewModelListener; }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterEntryBinding binding;

        public ViewHolder(AdapterEntryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
