package com.samwolfand.movieland.ui.otto;

import android.support.annotation.NonNull;

import com.samwolfand.movieland.data.model.Movie;

/**
 * Created by wkh176 on 12/14/15.
 */
public class FavoriteClickedEvent {

    @NonNull
    final Movie movie;
    int position;

    public FavoriteClickedEvent(@NonNull Movie movie, int position) {
        this.movie = movie;
        this.position = position;
    }

    @NonNull
    public Movie getMovie() {
        return movie;
    }

    public int getPosition() {
        return position;
    }
}
