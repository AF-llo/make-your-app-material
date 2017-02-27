package com.example.xyzreader.ui.activities;

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
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.data.UpdaterService;
import com.example.xyzreader.databinding.ActivityArticleListBinding;
import com.example.xyzreader.ui.adapter.BindingRecyclerAdapter;
import com.example.xyzreader.ui.adapter.IRecyclerViewItemClickListener;
import com.example.xyzreader.ui.model.ArticleItemViewModel;
import com.example.xyzreader.util.ArticleItemsProvider;

/**
 * Created by lars on 15.02.17.
 */

public class ArticlesActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor>, IRecyclerViewItemClickListener<ArticleItemViewModel> {

    private static final String EXTRA_REFRESHING = "key_extra_refreshing";

    private ActivityArticleListBinding mBinding;

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

    @SuppressWarnings({"ConstantConditions", "unchecked"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_list);
        mBinding.setArticlesActivity(this);
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);

        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportLoaderManager().initLoader(0, null, this);

        if (savedInstanceState == null) {
            refreshContent();
        } else {
            isRefreshing = savedInstanceState.getParcelable(EXTRA_REFRESHING);
        }
    }

    private void refreshContent() {
        if (!isRefreshing.get()) {
            startService(new Intent(this, UpdaterService.class));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(mRefreshingReceiver, new IntentFilter(UpdaterService.BROADCAST_ACTION_STATE_CHANGE));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mRefreshingReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding.swipeRefreshLayout.setOnRefreshListener(this);
        mBinding = null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EXTRA_REFRESHING, isRefreshing);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRefresh() {
        refreshContent();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        articleItems.clear();
        articleItems.addAll(ArticleItemsProvider.fromCursor(this, data));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onItemClick(ArticleItemViewModel item, View view, int position, BindingRecyclerAdapter adapter) {
        DetailsActivity.start(this, item.getId());
    }
}
