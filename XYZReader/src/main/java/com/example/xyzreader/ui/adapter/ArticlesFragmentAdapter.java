package com.example.xyzreader.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.xyzreader.ui.fragments.DetailsFragment;
import com.example.xyzreader.ui.model.ArticleItemViewModel;
import com.example.xyzreader.util.TransitionHelper;

/**
 * Created by lars on 16.02.17.
 */

public class ArticlesFragmentAdapter extends ObservableFragmentAdapter<ArticleItemViewModel> {
    
    public ArticlesFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(ArticleItemViewModel articleItemViewModel, int position) {
        return DetailsFragment.newInstance(articleItemViewModel, TransitionHelper.getImageTransitionName(position));
    }
    
}
