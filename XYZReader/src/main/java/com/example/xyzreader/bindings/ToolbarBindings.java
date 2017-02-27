package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.support.v7.widget.Toolbar;

/**
 * Created by lars on 30.01.17.
 */

public class ToolbarBindings {

    @BindingAdapter("homeIcon")
    public static void setHomeIcon(Toolbar toolbar, int ressourceId) {
        toolbar.setNavigationIcon(ressourceId);
    }

}
