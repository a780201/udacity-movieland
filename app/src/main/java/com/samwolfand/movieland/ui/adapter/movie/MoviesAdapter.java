package com.samwolfand.movieland.ui.adapter.movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.samwolfand.movieland.R;
import com.samwolfand.movieland.data.model.Movie;
import com.samwolfand.movieland.ui.activity.MovieActivity;
import com.samwolfand.movieland.ui.activity.MoviesActivity;
import com.samwolfand.movieland.ui.otto.BusProvider;
import com.samwolfand.movieland.ui.otto.FavoriteClickedEvent;
import com.samwolfand.movieland.ui.otto.MovieClickedEvent;

import java.util.List;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;

/**
 * Created by wkh176 on 12/14/15.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movie> movies;


    public MoviesAdapter(List<Movie> movies) {
        if (movies == null) {
            throw new NullPointerException("Movies were null");
        }
        this.movies = movies;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(itemView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(movies.get(position));
//
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public Movie getItem(int position) {
        return movies.get(position);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.movie_item_container)
        View mContentContainer;
        @Bind(R.id.movie_item_image)
        ImageView mImageView;
        @Bind(R.id.movie_item_title)
        TextView mTitleView;
        @Bind(R.id.movie_item_genres)
        TextView mGenresView;
        @Bind(R.id.movie_item_footer)
        View mFooterView;
        @Bind(R.id.movie_item_btn_favorite)
        ImageButton mFavoriteButton;

        @BindColor(R.color.color_primary)
        int mColorBackground;
        @BindColor(R.color.body_text_white)
        int mColorTitle;
        @BindColor(R.color.body_text_1_inverse)
        int mColorSubtitle;

        private long mMovieId;
        private View itemView;
        private Context parentContext;

        public MovieViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.itemView = itemView;
            this.parentContext = context;
        }

        public void bind(@NonNull final Movie movie) {
            mFavoriteButton.setSelected(movie.isFavored());
            mTitleView.setText(movie.getTitle());
            mContentContainer.setOnClickListener(v -> {
                View innerContainer = itemView.findViewById(R.id.container_inner_item);
                Intent startIntent = new Intent(getParentContext(), MovieActivity.class);
                startIntent.putExtra(MovieActivity.EXTRA_MOVIE, movie);
                ActivityOptionsCompat options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation((MoviesActivity) v.getContext(), innerContainer, MovieActivity
                                .TRANSITION_SHARED_ELEMENT);
                ActivityCompat.startActivity((MoviesActivity) v.getContext(), startIntent, options.toBundle());
            });
            mFavoriteButton.setOnClickListener(v -> {
                BusProvider.getInstance()
                        .post(new FavoriteClickedEvent(movie, MovieViewHolder.this.getAdapterPosition()));
                mFavoriteButton.setSelected(movie.isFavored());
            });

            // prevents unnecessary color blinking
            if (mMovieId != movie.getId()) {
                resetColors();
                mMovieId = movie.getId();
            }

            Glide.with(itemView.getContext())
                    .load("http://image.tmdb.org/t/p/w185/" + movie.getPosterPath())
                    .crossFade()
                    .placeholder(R.color.movie_poster_placeholder)
                    .listener(GlidePalette.with(movie.getPosterPath())
                            .intoCallBack(palette -> applyColors(palette.getVibrantSwatch())))
                    .into(mImageView);
        }

        private void resetColors() {
            mFooterView.setBackgroundColor(mColorBackground);
            mTitleView.setTextColor(mColorTitle);
            mGenresView.setTextColor(mColorSubtitle);
            mFavoriteButton.setColorFilter(mColorTitle, PorterDuff.Mode.MULTIPLY);
        }

        private void applyColors(Palette.Swatch swatch) {
            if (swatch != null) {
                mFooterView.setBackgroundColor(swatch.getRgb());
                mTitleView.setTextColor(swatch.getBodyTextColor());
                mGenresView.setTextColor(swatch.getTitleTextColor());
                mFavoriteButton.setColorFilter(swatch.getBodyTextColor(), PorterDuff.Mode.MULTIPLY);
            }
        }

        public Context getParentContext() {
            return parentContext;
        }
    }
}
