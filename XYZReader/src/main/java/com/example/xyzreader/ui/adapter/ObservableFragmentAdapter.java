package com.example.xyzreader.ui.adapter;

import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lars on 16.02.17.
 */

public abstract class ObservableFragmentAdapter<TItem> extends FragmentStatePagerAdapter {

    protected ObservableList<TItem> mItems;

    public ObservableFragmentAdapter(FragmentManager fm) {
        super(fm);
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
        return getItem(mItems.get(position),position);
    }

    public abstract Fragment getItem(TItem item,int position);

    @Override
    public int getCount() {
        return mItems.size();
    }

}
