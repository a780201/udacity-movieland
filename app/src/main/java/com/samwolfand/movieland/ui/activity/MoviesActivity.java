package com.samwolfand.movieland.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.samwolfand.movieland.R;
import com.samwolfand.movieland.data.api.Sort;
import com.samwolfand.movieland.data.model.Movie;
import com.samwolfand.movieland.data.repository.MoviesRepository;
import com.samwolfand.movieland.ui.adapter.movie.MoviesAdapter;
import com.samwolfand.movieland.ui.adapter.spinner.ModeSpinnerAdapter;
import com.samwolfand.movieland.ui.module.MovieModule;
import com.samwolfand.movieland.ui.otto.BusProvider;
import com.samwolfand.movieland.ui.otto.MovieClickedEvent;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import timber.log.Timber;

public class MoviesActivity extends BaseActivity {

    private static final String MODE_FAVORITES = "favorites";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private ModeSpinnerAdapter spinnerAdapter = new ModeSpinnerAdapter();
    private MoviesAdapter moviesAdapter;
    private String mMode;

    @Inject
    MoviesRepository moviesRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.bind(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        initModeSpinner();
        loadMovies();
    }

    public void setMovie(List<Movie> movies) {
        moviesAdapter = new MoviesAdapter(movies);
        recyclerView.setAdapter(moviesAdapter);
    }

    private void initModeSpinner() {
        Toolbar toolbar = getToolbar();
        if (toolbar == null)
            return;


        spinnerAdapter.clear();
        spinnerAdapter.addItem(MODE_FAVORITES, getString(R.string.mode_favored), false);
        spinnerAdapter.addHeader(getString(R.string.mode_sort));
        spinnerAdapter.addItem(Sort.POPULARITY.toString(), getString(R.string.mode_sort_popularity), false);
        spinnerAdapter.addItem(Sort.VOTE_COUNT.toString(), getString(R.string.mode_sort_vote_count), false);
        spinnerAdapter.addItem(Sort.VOTE_AVERAGE.toString(), getString(R.string.mode_sort_vote_average), false);

        int itemToSelect = -1;

        View spinnerContainer = LayoutInflater.from(this).inflate(R.layout.widget_toolbar_spinner, toolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        toolbar.addView(spinnerContainer, lp);

        Spinner spinner = (Spinner) spinnerContainer.findViewById(R.id.mode_spinner);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> spinner, View view, int position, long itemId) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (itemToSelect >= 0) {
            Timber.d("Restoring item selection to mode spinner: " + itemToSelect);
            spinner.setSelection(itemToSelect);
        }
    }

    public void loadMovies() {
        moviesRepository.discoverMovies(Sort.POPULARITY, 3)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::setMovie);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        BusProvider.getInstance().unregister(this);
    }

    @Override
    protected List<Object> getModules() {
        return Collections.<Object>singletonList(new MovieModule());
    }


    @Subscribe public void MovieClicked(MovieClickedEvent event) {

    }


}
