package com.example.xyzreader.ui.adapter;

import android.view.View;

/**
 * Created by Steve on 18.03.2016.
 * <br/>
 * Wird genutzt um eine onItemClick-Funktionalität zu implementieren.
 */
public interface IRecyclerViewItemClickListener<T> {
    void onItemClick(T item, View view, int position, BindingRecyclerAdapter<T> adapter);
}
