package com.example.xyzreader.ui.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

/**
 * Created by André on 18.03.2016.
 * <br/>
 * Wird genutzt um eine onItemLongClick-Funktionalität zu implementieren.
 */
public interface IRecyclerViewItemLongClickListener<T, V extends ViewDataBinding> {
    boolean onItemLongClick(T item, View view, int position, BindingRecyclerAdapter<T, V> adapter);
}
