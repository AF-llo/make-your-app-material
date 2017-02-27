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
import com.example.xyzreader.util.TransitionHelper;

/**
 * Created by lars on 27.02.17.
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        Fragment detailsPagerFragment = getSupportFragmentManager().findFragmentByTag(DetailsPagerFragment.TAG);
        if (detailsPagerFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content, ArticlesFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            TransitionHelper.resetRequrestedArticleId();
            manager.popBackStackImmediate();
        } else {
            super.onBackPressed();
        }
    }
}
