package com.samwolfand.movieland;

import android.app.Application;
import android.content.Context;

import dagger.ObjectGraph;
import timber.log.Timber;

/**
 * Created by wkh176 on 12/14/15.
 */
public class MoviesApplication extends Application {

    private ObjectGraph objectGraph;

    public static MoviesApplication get(Context context) {
        return (MoviesApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        objectGraph = initializeObjectGraph();

        Timber.plant(new Timber.DebugTree());
    }

    public ObjectGraph buildScopedObjectGraph(Object... modules) {
        return objectGraph.plus(modules);
    }

    public void inject(Object o) {
        objectGraph.inject(o);
    }

    private ObjectGraph initializeObjectGraph() {
        return buildInitialObjectGraph(new ApplicationModule(this));
    }

    private ObjectGraph buildInitialObjectGraph(Object... modules) {
        return ObjectGraph.create(modules);
    }
}
