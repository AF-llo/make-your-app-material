package com.example.xyzreader.ui.activities;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.xyzreader.R;
import com.example.xyzreader.ui.fragments.ArticlesFragment;
import com.example.xyzreader.ui.fragments.DetailsPagerFragment;

/**
 * Created by lars on 27.02.17.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String FRAGMENT_TAG = "main_content_fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        FragmentManager manager = getSupportFragmentManager();
        Fragment pagerFragment = manager.findFragmentByTag(DetailsPagerFragment.TAG);
        Fragment articlesFragment = manager.findFragmentByTag(FRAGMENT_TAG);
        if (pagerFragment != null) {
            manager.beginTransaction()
                    .replace(R.id.content, pagerFragment)
                    .commit();
        } else if (articlesFragment == null) {
            manager.beginTransaction()
                    .add(R.id.content, ArticlesFragment.newInstance (), FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            super.onBackPressed();
        }
    }
}
