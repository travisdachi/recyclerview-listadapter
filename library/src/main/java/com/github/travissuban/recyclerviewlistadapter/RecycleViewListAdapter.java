package com.github.travissuban.recyclerviewlistadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecycleViewListAdapter<T, VH extends ItemViewHolder<T, VH>> extends RecyclerView.Adapter<VH> {

    protected List<T> list = new ArrayList<>();
    protected OnItemClickListener<T, VH> onItemClickListener;
    protected DiffCalculator<T> diffCalculator;

    /**
     * @param items Initial list of items, leave it null if you don't want to initialize items.
     *              Keep in mind that {@link RecycleViewListAdapter} keep the items in its own internal List<T>
     */
    public RecycleViewListAdapter(@Nullable List<T> items, @Nullable OnItemClickListener<T, VH> onItemClickListener, @Nullable DiffCalculator<T> diffCalculator) {
        this.onItemClickListener = onItemClickListener;
        this.diffCalculator = diffCalculator;
        if (items != null) {
            this.list.addAll(items);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = onCreateViewHolder(parent, viewType, LayoutInflater.from(parent.getContext()));
        vh.setOnItemClickListener(onItemClickListener);
        return vh;
    }

    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.item = list.get(position);
        onBindViewHolder(holder, position, holder.item);
    }

    protected abstract void onBindViewHolder(@NonNull VH holder, int position, T item);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getItems() {
        return new ArrayList<>(list);
    }

    public void setItems(List<T> newList) {
        if (diffCalculator != null) {
            DiffUtil.DiffResult diffResult = diffCalculator.getDiffResult(list, newList);
            list.clear();
            list.addAll(newList);
            diffResult.dispatchUpdatesTo(this);
        } else {
            list.clear();
            list.addAll(newList);
            notifyDataSetChanged();
        }
    }
}
