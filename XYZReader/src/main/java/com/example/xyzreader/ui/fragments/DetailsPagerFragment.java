package com.example.xyzreader.ui.fragments;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.databinding.LayoutArticleDetailsBinding;
import com.example.xyzreader.ui.adapter.ArticlesFragmentAdapter;
import com.example.xyzreader.ui.model.ArticleItemViewModel;

import java.util.ArrayList;

/**
 * Created by lars on 27.02.17.
 */

public class DetailsPagerFragment extends Fragment {

    public static final String TAG = DetailsPagerFragment.class.getSimpleName();

    private static final String EXTRA_POSITION = "extra_position";
    private static final String EXTRA_ARTICLES = "extra_articles";

    private static final int NOT_DEFINED = -1;

    private LayoutArticleDetailsBinding mBinding;

    public ObservableList<ArticleItemViewModel> mArticles = new ObservableArrayList<>();

    public int position = NOT_DEFINED;

    public static Fragment newInstance(int position, ArrayList<ArticleItemViewModel> articles) {
        Bundle arguments = new Bundle();
        arguments.putInt(EXTRA_POSITION, position);
        arguments.putParcelableArrayList(EXTRA_ARTICLES, articles);
        DetailsPagerFragment fragment = new DetailsPagerFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    public DetailsPagerFragment() {
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        postponeEnterTransition();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        }
        setSharedElementReturnTransition(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_article_details, container, false);
        mBinding.setDetailsPagerFragment(this);
        return mBinding.getRoot();
    }

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        Bundle extras = getArguments();
        if (savedInstanceState == null) {
            if (mArticles.size() == 0) {
                mArticles.addAll((ArrayList) extras.getParcelableArrayList(EXTRA_ARTICLES));
            }
            position = extras.getInt(EXTRA_POSITION);
            ArticlesFragmentAdapter adapter = new ArticlesFragmentAdapter(getChildFragmentManager());
            adapter.setItems(mArticles);
            mBinding.articlePager.setAdapter(adapter);
            mBinding.articlePager.setCurrentItem(position, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
        }
        return super.onOptionsItemSelected(item);
    }

    public void back() {
        getActivity().onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

}
