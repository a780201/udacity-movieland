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



import com.samwolfand.movieland.data.model.Genre;

import java.util.Map;

import rx.Observable;


/**
 * A facade for which Fragments and Activities can use to
 * get the data needed to display without understanding
 * how the data is retrieved
 *
 * @see GenresRepositoryImpl
 */
public interface GenresRepository {

    Observable<Map<Integer, Genre>> genres();

}
