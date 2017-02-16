package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by lars on 15.02.17.
 */

public class SwypeRefreshBindings {

    @BindingAdapter("bind:refreshing")
    public static void setRefreshing(SwipeRefreshLayout swipeRefreshLayout, boolean isRefreshing) {
        swipeRefreshLayout.setRefreshing(isRefreshing);
    }

}
