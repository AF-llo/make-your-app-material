package com.example.xyzreader.ui.adapter;

import com.example.xyzreader.BR;
import com.example.xyzreader.R;
import com.example.xyzreader.ui.model.ArticleItemViewModel;

/**
 * Created by lars on 15.02.17.
 */

public class ArticlesRecyclerAdapter extends BindingRecyclerAdapter<ArticleItemViewModel> {

    // TODO: 15.02.17 use different layout for landscape and portrait ?

    public ArticlesRecyclerAdapter() {
        super(BR.article, R.layout.layout_article_list_item);
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
