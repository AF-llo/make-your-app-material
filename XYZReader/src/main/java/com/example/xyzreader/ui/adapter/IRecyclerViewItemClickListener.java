package com.example.xyzreader.ui.adapter;

import android.databinding.ViewDataBinding;

/**
 * Created by Steve on 18.03.2016.
 * <br/>
 * Wird genutzt um eine onItemClick-Funktionalität zu implementieren.
 */
public interface IRecyclerViewItemClickListener<T, V extends ViewDataBinding> {
    void onItemClick(T item, V binding, int position, BindingRecyclerAdapter<T, V> adapter);
}
