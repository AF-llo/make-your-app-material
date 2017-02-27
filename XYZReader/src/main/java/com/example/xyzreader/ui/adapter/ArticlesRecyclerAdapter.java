package com.example.xyzreader.ui.adapter;

import android.content.Context;
import android.os.Build;

import com.bumptech.glide.Glide;
import com.example.xyzreader.BR;
import com.example.xyzreader.R;
import com.example.xyzreader.databinding.LayoutArticleListItemBinding;
import com.example.xyzreader.ui.model.ArticleItemViewModel;
import com.example.xyzreader.util.TransitionHelper;

/**
 * Created by lars on 15.02.17.
 */

public class ArticlesRecyclerAdapter extends BindingRecyclerAdapter<ArticleItemViewModel, LayoutArticleListItemBinding> {

    // TODO: 15.02.17 use different layout for landscape and portrait ?

    public ArticlesRecyclerAdapter() {
        super(BR.article, R.layout.layout_article_list_item);
        setHasStableIds(true);
    }

    @Override
    public void onItemBound(ArticleItemViewModel articleItemViewModel, LayoutArticleListItemBinding binding, int position) {
        Context context = binding.image.getContext();
        Glide.with(context)
                .load(articleItemViewModel.getThumbImageUrl())
                .centerCrop()
                .placeholder(R.drawable.empty_detail)
                .into(binding.image);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.image.setTransitionName(TransitionHelper.getImageTransitionName(position));
        }
    }

    @Override
    public long getItemId(int position) {
        ArticleItemViewModel item;
        try {
            item = getItems().get(position);
        } catch (IndexOutOfBoundsException ioe) {
            return super.getItemId(position);
        }
        return item.getId();
    }
}
