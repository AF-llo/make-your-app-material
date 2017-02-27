package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.xyzreader.R;
import com.example.xyzreader.ui.adapter.BindingRecyclerAdapter;
import com.example.xyzreader.ui.adapter.IRecyclerViewItemClickListener;
import com.example.xyzreader.ui.adapter.IRecyclerViewItemLongClickListener;

/**
 * Created by lars on 15.02.17.
 */

public class RecyclerViewBindings {

    private static final String TAG = RecyclerViewBindings.class.getSimpleName();

    @BindingAdapter({"layoutManager"})
    public static void setLayoutManager(RecyclerView recyclerView, RecyclerView.LayoutManager manager) {
        if (manager != null) {
            recyclerView.setLayoutManager(manager);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"items", "adapter"})
    public static <T, V extends ViewDataBinding> void setItemsWithAdapter(RecyclerView recyclerView, ObservableArrayList<T> items, BindingRecyclerAdapter<T, V> adapter) {
        if (adapter != null && recyclerView.getAdapter() == null) {
            if (recyclerView.getLayoutManager() == null) {
                recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
            }
            if (items != null) {
                adapter.setItems(items);
            }
            recyclerView.setAdapter(adapter);

            addListener(recyclerView, adapter);
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter({"items", "adapter"})
    public static <T, V extends ViewDataBinding> void setItemsWithClass(RecyclerView recyclerView, ObservableArrayList<T> items, String adapterClass) {
        try {
            BindingRecyclerAdapter<T, V> adapter = (BindingRecyclerAdapter<T, V>) recyclerView.getAdapter();
            if (adapter == null) {
                adapter = (BindingRecyclerAdapter<T, V>) Class.forName(adapterClass).newInstance();
                if (recyclerView.getLayoutManager() == null) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                }
                adapter.setItems(items);
                recyclerView.setAdapter(adapter);

                addListener(recyclerView, adapter);
            }
        } catch (ClassNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (InstantiationException e) {
            Log.e(TAG, e.getMessage(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <T, V extends ViewDataBinding> void addListener(RecyclerView recyclerView, BindingRecyclerAdapter<T, V> adapter) {

        if (recyclerView.getTag(R.id.click_listener_v2_tag) != null && recyclerView.getTag(R.id.click_listener_v2_tag) instanceof IRecyclerViewItemClickListener) {
            adapter.setOnItemClickListener((IRecyclerViewItemClickListener<T, V>) recyclerView.getTag(R.id.click_listener_v2_tag));
        }

        if (recyclerView.getTag(R.id.long_click_listener_v2_tag) != null && recyclerView.getTag(R.id.long_click_listener_v2_tag) instanceof IRecyclerViewItemLongClickListener) {
            adapter.setOnItemLongClickListener((IRecyclerViewItemLongClickListener<T, V>) recyclerView.getTag(R.id.long_click_listener_v2_tag));
        }
    }

    @SuppressWarnings("unchecked")
    @BindingAdapter("onItemClick")
    public static <T, V extends ViewDataBinding> void setOnItemClickListener(RecyclerView recyclerView, IRecyclerViewItemClickListener<T, V> itemClickListener) {
        if (recyclerView.getAdapter() == null) {
            recyclerView.setTag(R.id.click_listener_v2_tag, itemClickListener);
        } else {
            ((BindingRecyclerAdapter<T, V>) recyclerView.getAdapter()).setOnItemClickListener(itemClickListener);
        }
    }

}
