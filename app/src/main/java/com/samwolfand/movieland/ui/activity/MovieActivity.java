package com.samwolfand.movieland.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


import com.samwolfand.movieland.R;
import com.samwolfand.movieland.data.model.Movie;
import com.samwolfand.movieland.ui.fragment.MovieFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieActivity extends BaseActivity {
    public static final String EXTRA_MOVIE = "movie_extra";
    public static final String TRANSITION_SHARED_ELEMENT = "title";
    private static final String MOVIE_FRAGMENT_TAG = "fragment_movie";
    @Bind(R.id.movie_details_container)
    FrameLayout movieDetailsContainer;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);


        if (toolbar != null) {
            ViewCompat.setElevation(toolbar, 8);
            toolbar.setNavigationOnClickListener(view -> finish());

            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
                ab.setDisplayHomeAsUpEnabled(true);
                ab.setDisplayShowHomeEnabled(true);
            }
        }
        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        ViewCompat.setTransitionName(movieDetailsContainer, TRANSITION_SHARED_ELEMENT);
        if (savedInstanceState == null) {
            MovieFragment fragment = MovieFragment.newInstance(movie);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_details_container, fragment, MOVIE_FRAGMENT_TAG)
                    .commit();
        }
    }
}

