package com.github.travissuban.recyclerviewlistadapter;

import android.view.View;

/**
 * @param <T>  the type of your item
 * @param <VH> the type of your ViewHolder
 */
public interface OnItemClickListener<T, VH> {
    /**
     * @param view     the view receiving the click event.
     *                 In case of your itemView has multiple clickable views, use this to identify which one.
     * @param position the position of the item in the adapter
     * @param holder   the ViewHolder, your ViewHolder
     * @param item     the item associate with the itemView
     */
    void onClick(View view, int position, VH holder, T item);
}
