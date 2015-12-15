package com.samwolfand.movieland;

import android.app.Application;

import com.samwolfand.movieland.data.DataModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by wkh176 on 12/14/15.
 */

@Module(
        includes = DataModule.class,
        injects = {
                MoviesApplication.class
        },
        library = true
)
public class ApplicationModule {

    private final MoviesApplication application;

    public ApplicationModule(MoviesApplication moviesApplication) {
        this.application = moviesApplication;

    }

    @Provides @Singleton
    Application provideApplication() {
        return application;
    }
}
