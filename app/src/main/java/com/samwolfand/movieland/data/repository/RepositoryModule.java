/*
 * Copyright 2015.  Emin Yahyayev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.samwolfand.movieland.data.repository;


import android.content.ContentResolver;


import com.samwolfand.movieland.data.api.MoviesApi;
import com.squareup.sqlbrite.BriteContentResolver;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(complete = false, library = true)
public final class RepositoryModule {

    @Singleton
    @Provides
    public GenresRepository providesGenresRepository(MoviesApi moviesApi, BriteContentResolver contentResolver) {
        return new GenresRepositoryImpl(moviesApi, contentResolver);
    }

    @Singleton
    @Provides
    public MoviesRepository providesMoviesRepository(MoviesApi moviesApi, ContentResolver contentResolver,
                                                     BriteContentResolver briteContentResolver, GenresRepository repository) {
        return new MoviesRepositoryImpl(moviesApi, contentResolver, briteContentResolver, repository);
    }

}
