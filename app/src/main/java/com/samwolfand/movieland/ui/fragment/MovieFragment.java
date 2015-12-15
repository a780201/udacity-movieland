package com.samwolfand.movieland.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.samwolfand.movieland.R;
import com.samwolfand.movieland.data.model.Movie;
import com.samwolfand.movieland.data.repository.MoviesRepository;
import com.samwolfand.movieland.ui.activity.MovieActivity;
import com.samwolfand.movieland.ui.module.MovieModule;
import com.samwolfand.movieland.ui.otto.BusProvider;
import com.samwolfand.movieland.ui.otto.FavoriteClickedEvent;
import com.samwolfand.movieland.ui.otto.MovieClickedEvent;
import com.samwolfand.movieland.ui.widget.AspectLockedImageView;
import com.samwolfand.movieland.util.PrefUtils;
import com.samwolfand.movieland.util.UiUtils;
import com.squareup.otto.Subscribe;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends BaseFragment implements ObservableScrollViewCallbacks {
    public static final String ARG_MOVIE = "arg_movie";
    private Movie mMovie;


    @Nullable
    Toolbar mToolbar;
    @Bind(R.id.movie_cover)
    AspectLockedImageView mCoverImage;
    @Bind(R.id.movie_poster_play)
    ImageView mPosterPlayImage;
    @Bind(R.id.movie_cover_container)
    FrameLayout mCoverContainer;
    @Bind(R.id.movie_poster)
    AspectLockedImageView mPosterImage;
    @Bind(R.id.movie_favorite_button)
    ImageButton mFavoriteButton;
    @Bind(R.id.movie_title)
    TextView mTitle;
    @Bind(R.id.movie_release_date)
    TextView mReleaseDate;
    @Bind(R.id.movie_average_rating)
    TextView mRating;
    @Bind(R.id.movie_overview)
    TextView mOverview;
    @Bind(R.id.movie_reviews_container)
    ViewGroup mReviewsGroup;
    @Bind(R.id.movie_videos_container)
    ViewGroup mVideosGroup;

    @Bind(R.id.movie_scroll_view)
    ObservableScrollView mScrollView;
    @BindColor(R.color.color_primary)
    int colorPrimary;

    @BindColor(R.color.material_white)
    int colorWhite;

    @Inject
    MoviesRepository movieRepository;

    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MovieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(Movie movie) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, movie);
        MovieFragment fragment = new MovieFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initToolbar();
        mScrollView.setScrollViewCallbacks(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onScrollChanged(mScrollView.getCurrentScrollY(), false, false);

        onMovieLoaded(getArguments().getParcelable(ARG_MOVIE));
    }

    private void initToolbar() {
        if (getActivity() instanceof MovieActivity) {
            MovieActivity activity = ((MovieActivity) getActivity());
            mToolbar = (Toolbar) activity.findViewById(R.id.toolbar);
            activity.setSupportActionBar(mToolbar);
            ActionBar actionBar = activity.getSupportActionBar();
            if (actionBar != null) {
                actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private void onMovieLoaded(Movie movie) {
        mMovie = movie;

        if (mToolbar != null) {
            mToolbar.setTitle(mMovie.getTitle());
        }

        mTitle.setText(movie.getTitle());
        mRating.setText(getString(R.string.movie_details_rating, movie.getVoteAverage()));
        mReleaseDate.setText(UiUtils.getDisplayReleaseDate(movie.getReleaseDate()));
        mOverview.setText(movie.getOverview());


        Glide.with(this).load("http://image.tmdb.org/t/p/w342/" + movie.getBackdropPath())
                .placeholder(R.color.movie_cover_placeholder)
                .centerCrop()
                .crossFade()
                .into(mCoverImage);

        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                .centerCrop()
                .crossFade()
                .into(mPosterImage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        ViewCompat.setTranslationY(mCoverContainer, scrollY / 2);

        if (mToolbar != null) {
            int parallaxImageHeight = mCoverContainer.getMeasuredHeight();
            float alpha = Math.min(1, (float) scrollY / parallaxImageHeight);
            mToolbar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, colorPrimary));
            mToolbar.setTitleTextColor(ScrollUtils.getColorWithAlpha(alpha, colorWhite));
        }
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    @Override
    protected List<Object> getModules() {
        return Collections.<Object>singletonList(new MovieModule());
    }

    // OTTO EVENTS


    @Subscribe
    public void onMovieClicked(MovieClickedEvent event) {
        Toast.makeText(getContext(), event.getOtto(), Toast.LENGTH_SHORT).show();

    }


}
