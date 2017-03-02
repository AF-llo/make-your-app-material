package com.example.xyzreader.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.UpdaterService;
import com.example.xyzreader.databinding.LayoutArticleListBinding;
import com.example.xyzreader.databinding.LayoutArticleListItemBinding;
import com.example.xyzreader.ui.adapter.BindingRecyclerAdapter;
import com.example.xyzreader.ui.adapter.IRecyclerViewItemClickListener;
import com.example.xyzreader.ui.model.ArticleItemViewModel;
import com.example.xyzreader.util.ArticleItemsProvider;
import com.example.xyzreader.util.TransitionHelper;

import java.util.List;

/**
 * Created by lars on 27.02.17.
 */

public class ArticlesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>, IRecyclerViewItemClickListener<ArticleItemViewModel, LayoutArticleListItemBinding> {

    private static final String TAG = ArticlesFragment.class.getSimpleName();

    private static final String EXTRA_REFRESHING = "key_extra_refreshing";

    private LayoutArticleListBinding mBinding;

    public ObservableBoolean isRefreshing = new ObservableBoolean(false);

    public ObservableArrayList<ArticleItemViewModel> articleItems = new ObservableArrayList<>();

    private BroadcastReceiver mRefreshingReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (UpdaterService.BROADCAST_ACTION_STATE_CHANGE.equals(intent.getAction())) {
                isRefreshing.set(intent.getBooleanExtra(UpdaterService.EXTRA_REFRESHING, false));
            }
        }
    };

    public static ArticlesFragment newInstance() {
        return new ArticlesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.layout_article_list, container, false);
        mBinding.setArticlesFragment(this);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mBinding.setLayoutManager(new GridLayoutManager(getContext(), getResources().getInteger(R.integer.list_column_count)));

        ((AppCompatActivity)getActivity()).setSupportActionBar(mBinding.toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        getActivity().getSupportLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            refreshContent();
        } else {
            isRefreshing = savedInstanceState.getParcelable(EXTRA_REFRESHING);
        }
        return mBinding.getRoot();
    }

    private void refreshContent() {
        if (!isRefreshing.get()) {
            getContext().startService(new Intent(getContext(), UpdaterService.class));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getContext().registerReceiver(mRefreshingReceiver, new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    public void onStop() {
        super.onStop();
        getContext().unregisterReceiver(mRefreshingReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mBinding = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_REFRESHING, isRefreshing);
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        List<ArticleItemViewModel> newItems = ArticleItemsProvider.fromCursor(getContext(), data);
        if (newItems.size() != articleItems.size()) {
            articleItems.clear();
            articleItems.addAll(newItems);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @SuppressWarnings("unchecked")
    @Override
    public void onItemClick(ArticleItemViewModel item, LayoutArticleListItemBinding binding, int position, BindingRecyclerAdapter adapter) {
        String transitionName = TransitionHelper.getImageTransitionName(position);
        getFragmentManager()
                .beginTransaction()
                .addSharedElement(binding.image, transitionName)
                .addToBackStack(TAG)
                .replace(R.id.content, DetailsPagerFragment.newInstance(position, adapter.getItems()))
                .commit();
    }

}
