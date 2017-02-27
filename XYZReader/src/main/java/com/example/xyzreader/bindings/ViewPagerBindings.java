package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.databinding.ObservableList;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.xyzreader.ui.adapter.ObservableFragmentAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by lars on 16.02.17.
 */

public class ViewPagerBindings {

    private static final String TAG = ViewPagerBindings.class.getSimpleName();

    @SuppressWarnings("unchecked")
    @BindingAdapter({"items","adapter","fragmentManager"})
    public static <T> void setItems(ViewPager viewPager, ObservableList<T> items, String adapterClass, FragmentManager fragmentManager) {
        try {
            ObservableFragmentAdapter<T> adapter = (ObservableFragmentAdapter<T>)viewPager.getAdapter();

            if(adapter == null) {
                Class<?> clazz = Class.forName(adapterClass);
                Constructor<?> ctor = clazz.getConstructor(FragmentManager.class);
                adapter = (ObservableFragmentAdapter<T>) ctor.newInstance(fragmentManager);

                adapter.setItems(items);
                viewPager.setAdapter(adapter);

                final ObservableFragmentAdapter<T> finalAdapter = adapter;
                items.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<T>>() {
                    @Override
                    public void onChanged(ObservableList<T> ts) {
                        finalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeChanged(ObservableList<T> ts, int i, int i1) {
                        finalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeInserted(ObservableList<T> ts, int i, int i1) {
                        finalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeMoved(ObservableList<T> ts, int i, int i1, int i2) {
                        finalAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemRangeRemoved(ObservableList<T> ts, int i, int i1) {
                        finalAdapter.notifyDataSetChanged();
                    }
                });

            }
        } catch (ClassCastException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (InstantiationException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (InvocationTargetException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (NoSuchMethodException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (IllegalAccessException e) {
            Log.e(TAG,e.getMessage(),e);
        } catch (ClassNotFoundException e) {
            Log.e(TAG,e.getMessage(),e);
        }
    }

}
