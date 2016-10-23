package com.github.travissuban.recyclerviewlistadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * A base view holder to use with {@link RecycleViewListAdapter}.
 * There's a handy {@link ItemViewHolder#callOnClickListener(View)} for sending a click event to your {@link OnItemClickListener}
 *
 * @param <T>  the type of your item
 * @param <VH> the type of your view holder - this view holder
 */
public abstract class ItemViewHolder<T, VH extends ItemViewHolder> extends RecyclerView.ViewHolder {

    /**
     * An item of your type, automatically assigned from the list in the adapter.
     */
    public T item;
    private OnItemClickListener<T, VH> onItemClickListener;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    /**
     * Call this method to notify your {@link OnItemClickListener} in your {@link RecycleViewListAdapter}.
     * You don't have to call this for your itemView, it has already been done for you.
     * If your itemView has multiple clickable views, you can call this in their {@link android.view.View.OnClickListener}.
     *
     * @param view the view receiving the click event.
     */
    protected void callOnClickListener(@NonNull View view) {
        if (onItemClickListener != null)
            onItemClickListener.onClick(view, getAdapterPosition(), (VH) this, item);
    }

    /**
     * You shouldn't have to call this. The {@link RecycleViewListAdapter} sets this for you.
     *
     * @param listener the listener
     */
    void setOnItemClickListener(OnItemClickListener<T, VH> listener) {
        this.onItemClickListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callOnClickListener(v);
            }
        });
    }

    OnItemClickListener<T, VH> getOnItemClickListener() {
        return onItemClickListener;
    }
}
