package com.samwolfand.movieland.data.provider;

import android.app.Application;
import android.content.ContentResolver;

import com.squareup.sqlbrite.BriteContentResolver;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

/**
 * Created by wkh176 on 12/9/15.
 */

@Module(
        injects = {
                MovieProvider.class
        },
        complete = false,
        library = true
)
public class ProviderModule {

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    SqlBrite provideSqlBrite() {
        return SqlBrite.create(message -> Timber.tag("Database").v(message));
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    ContentResolver provideContentResolver(Application application) {
        return application.getContentResolver();
    }

    @Provides
    @Singleton
    @SuppressWarnings("unused")
    BriteContentResolver provideBrideContentResolver(SqlBrite sqlBrite, ContentResolver contentResolver) {
        return sqlBrite.wrapContentProvider(contentResolver);
    }
}
