package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.widget.TextView;

/**
 * Created by lars on 27.01.17.
 */

public class TextViewBindings {

    @BindingAdapter({"bind:textsequence"})
    public static void setSpannedText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
    }

}
