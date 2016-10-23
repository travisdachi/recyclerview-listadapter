package com.github.travissuban.recyclerviewlistadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

public abstract class RecycleViewListAdapter<T, VH extends ItemViewHolder<T, VH>> extends RecyclerView.Adapter<VH> {

    private List<T> list;
    protected OnItemClickListener<T, VH> onItemClickListener;
    private DiffUtil.Callback diffUtilCallback;
    private boolean diffDetectMove;

    /**
     * Instantiates a new {@link RecycleViewListAdapter}
     *
     * @param list                the list
     * @param onItemClickListener if you want to listen to the click event
     */
    public RecycleViewListAdapter(@NonNull List<T> list, @Nullable OnItemClickListener<T, VH> onItemClickListener) {
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        VH vh = onCreateViewHolder(parent, viewType, LayoutInflater.from(parent.getContext()));
        vh.setOnItemClickListener(onItemClickListener);
        return vh;
    }

    /**
     * @param parent   the parent
     * @param viewType the viewType
     * @param inflater the inflater
     * @return your view holder
     */
    protected abstract VH onCreateViewHolder(ViewGroup parent, int viewType, LayoutInflater inflater);

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.item = list.get(position);
        onBindViewHolder(holder, position, holder.item);
    }

    /**
     * @param holder   your view holder
     * @param position the position
     * @param item     the item, the same one as {@link ItemViewHolder#item}
     */
    protected abstract void onBindViewHolder(@NonNull VH holder, int position, T item);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public List<T> getList() {
        return list;
    }

    /**
     * Replaces the list.
     * <p>
     * See also {@link RecycleViewListAdapter#setDiffUtil(DiffUtil.Callback, boolean)}.
     *
     * @param newList the new list
     */
    public void setList(List<T> newList) {
        if (diffUtilCallback == null) {
            this.list = newList;
            notifyDataSetChanged();
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallback, diffDetectMove);
            this.list = newList;
            diffResult.dispatchUpdatesTo(this);
        }
    }

    /**
     * Add items and call {@link RecycleViewListAdapter#notifyItemRangeInserted(int, int)}
     *
     * @param items the items
     */
    public void addItems(@NonNull List<T> items) {
        int oldSize = getItemCount();
        this.list.addAll(items);
        notifyItemRangeInserted(oldSize, items.size());
    }

    /**
     * Just pass along the DiffUtil stuffs when you call {@link RecycleViewListAdapter#setList(List)}
     *
     * @param diffUtilCallback the diff util callback
     * @param diffDetectMove   the diff detect move
     */
    public void setDiffUtil(DiffUtil.Callback diffUtilCallback, boolean diffDetectMove) {
        this.diffUtilCallback = diffUtilCallback;
        this.diffDetectMove = diffDetectMove;
    }
}
