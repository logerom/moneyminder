package de.logerbyte.moneyminder.cashsummary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by logerom on 28.07.18.
 */

public class CashAdapter extends RecyclerView.Adapter<CashAdapter.ViewHolder> {


    private ArrayList<CashItem> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // TODO: 29.07.18 create view holder (with layout etc.)
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // TODO: 29.07.18 bind items to viewholder
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
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
