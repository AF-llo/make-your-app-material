package com.example.xyzreader.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lars on 23.02.17.
 */

public class MarginTextView extends TextView {
    public MarginTextView(Context context) {
        super(context);
    }

    public MarginTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarginTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MarginTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setStartMargin(int margin) {
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) getLayoutParams();
        DisplayMetrics displaymetrics = getResources().getDisplayMetrics();
        int pxMargin = (int) (TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_PX, margin, displaymetrics ) * displaymetrics.density);
        Log.d(this.getClass().getSimpleName(), "pxMargin: " + pxMargin + ", density: " + displaymetrics.density);
        params.leftMargin = pxMargin;
        setLayoutParams(params);
    }
}
