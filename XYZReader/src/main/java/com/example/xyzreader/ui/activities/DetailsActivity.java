package com.example.xyzreader.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.xyzreader.R;
import com.example.xyzreader.data.ArticleLoader;
import com.example.xyzreader.databinding.ActivityArticleDetailsBinding;
import com.example.xyzreader.ui.model.ArticleItemViewModel;
import com.example.xyzreader.util.ArticleItemsProvider;

/**
 * Created by lars on 16.02.17.
 */

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private static final String EXTRA_ARTICLE_ID = "extra_article_id";

    private static final int NOT_DEFINED = -1;

    private ActivityArticleDetailsBinding mBinding;

    public ObservableList<ArticleItemViewModel> mArticles = new ObservableArrayList<>();

    private long selectedId = NOT_DEFINED;

    public static void start(Context context, long articleId) {
        if (context != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // TODO: 16.02.17 transition with shared element
            } else {

            }
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra(EXTRA_ARTICLE_ID, articleId);
            context.startActivity(intent);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_article_details);
        mBinding.setDetailsActivity(this);
        mBinding.setFragmentManager(getSupportFragmentManager());

        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null && extras != null && extras.containsKey(EXTRA_ARTICLE_ID)) {
            selectedId = extras.getLong(EXTRA_ARTICLE_ID);
        }
        setSupportActionBar(mBinding.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinding = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return ArticleLoader.newAllArticlesInstance(this);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mArticles.clear();
        mArticles.addAll(ArticleItemsProvider.fromCursor(this, data));
        if (selectedId != NOT_DEFINED) {
            for (int position = 0; position < mArticles.size(); position++) {
                ArticleItemViewModel article = mArticles.get(position);
                if (article.getId() == selectedId) {
                    mBinding.articlePager.setCurrentItem(position, false);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
