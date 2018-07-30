package de.logerbyte.moneyminder.cashsummary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import de.logerbyte.moneyminder.R;
import de.logerbyte.moneyminder.databinding.AdapterEntryBinding;

/**
 * Created by logerom on 28.07.18.
 */

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.ViewHolder> {


    private ArrayList<CashItem> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_entry, parent);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CashItem cashItem = list.get(position);
        holder.binding.setViewModel(cashItem);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<CashItem> list) {
        this.list = list;
    }

    // TODO: 29.07.18 why an extra viewHolder?
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        AdapterEntryBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = AdapterEntryBinding.bind(itemView);
        }
    }
}
