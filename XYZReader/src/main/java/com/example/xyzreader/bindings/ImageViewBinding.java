package com.example.xyzreader.bindings;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.xyzreader.R;

/**
 * Created by lars on 15.02.17.
 */

public class ImageViewBinding {

    @BindingAdapter(value = {"bind:imageUrl", "bind:colorCallback"}, requireAll = false)
    public static void setImageUrl(ImageView imageView, String url, ImageColorCallback callback) {
        Glide.with(imageView.getContext()).load(url).centerCrop().placeholder(R.drawable.empty_detail).into(imageView);
    }

    public interface ImageColorCallback {
        void onImagePalete(int color);
    }

}
