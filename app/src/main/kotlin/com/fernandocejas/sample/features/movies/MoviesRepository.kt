package com.fernandocejas.sample.features.movies

import io.reactivex.Observable
import javax.inject.Inject

class MoviesRepository @Inject constructor() {

    //TODO: create data sources
    fun movies(): Observable<List<Movie>> {
        val movieOne = Movie.create { title = "Iron Man 3" }
        val movieTwo = Movie.create { title = "Superman" }

        return Observable.just(listOf(movieOne, movieTwo))
    }
}