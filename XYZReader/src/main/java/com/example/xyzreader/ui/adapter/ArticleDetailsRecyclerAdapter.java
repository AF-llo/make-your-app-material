package com.example.xyzreader.ui.adapter;

import com.example.xyzreader.BR;
import com.example.xyzreader.R;
import com.example.xyzreader.ui.model.ArticleItemViewModel;

/**
 * Created by lars on 20.02.17.
 */

public class ArticleDetailsRecyclerAdapter extends BindingRecyclerAdapter<ArticleItemViewModel> {

    public ArticleDetailsRecyclerAdapter() {
        super(BR.article, R.layout.layout_article_details_item);
        setHasStableIds(true);
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
