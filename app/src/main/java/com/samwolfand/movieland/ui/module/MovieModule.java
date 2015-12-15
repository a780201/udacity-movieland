package com.samwolfand.movieland.ui.module;

import com.samwolfand.movieland.ApplicationModule;
import com.samwolfand.movieland.data.repository.MoviesRepository;
import com.samwolfand.movieland.ui.activity.BaseActivity;
import com.samwolfand.movieland.ui.activity.MoviesActivity;
import com.samwolfand.movieland.ui.fragment.MovieFragment;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wkh176 on 12/14/15.
 */

@Module(
        injects = {
                MoviesActivity.class,
                MoviesRepository.class,
                BaseActivity.class,
                MovieFragment.class
        },
        addsTo = ApplicationModule.class
)
public class MovieModule {
}
