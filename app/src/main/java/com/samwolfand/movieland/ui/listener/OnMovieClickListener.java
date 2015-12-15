package com.samwolfand.movieland.ui.listener;

import android.support.annotation.NonNull;
import android.view.View;

import com.samwolfand.movieland.data.model.Movie;

/**
 * Created by wkh176 on 12/14/15.
 */
public interface OnMovieClickListener {

    void onContentClicked(@NonNull final Movie movie, View view, int position);

    void onFavoredClicked(@NonNull final Movie movie, int position);

    public static OnMovieClickListener DUMMY = new OnMovieClickListener() {
        @Override
        public void onContentClicked(@NonNull Movie movie, View view, int position) {
        }

        @Override
        public void onFavoredClicked(@NonNull Movie movie, int position) {
        }
    };
}
