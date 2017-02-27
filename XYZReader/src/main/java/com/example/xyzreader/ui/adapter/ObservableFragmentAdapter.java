package com.example.xyzreader.ui.adapter;

import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by lars on 16.02.17.
 */

public abstract class ObservableFragmentAdapter<TItem> extends FragmentPagerAdapter {

    private SparseArray<Fragment> mFragments;
    protected ObservableList<TItem> mItems;

    public ObservableFragmentAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new SparseArray<>();
    }

    public void setItems(ObservableList<TItem> items) {
        if(items == null) {
            throw new IllegalArgumentException(getClass().getName() + ": items must not be null.");
        }
        mItems = items;
    }

    public ObservableList<TItem> getItems() {
        return mItems;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = mFragments.get(position);
        if(fragment == null) {
            fragment = getItem(mItems.get(position),position);
            mFragments.put(position,fragment);
        }

        return fragment;
    }

    public abstract Fragment getItem(TItem item,int position);

    @Override
    public int getCount() {
        return mItems.size();
    }

}
