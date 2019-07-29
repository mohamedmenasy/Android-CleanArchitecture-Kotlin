/**
 * Copyright (C) 2018 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fernandocejas.sample.core.di

import com.fernandocejas.sample.BuildConfig
import com.fernandocejas.sample.core.navigation.Navigator
import com.fernandocejas.sample.core.platform.NetworkHandler
import com.fernandocejas.sample.features.login.Authenticator
import com.fernandocejas.sample.features.movies.data.remote.MoviesRepository
import com.fernandocejas.sample.features.movies.data.remote.MoviesService
import com.fernandocejas.sample.features.movies.usecase.GetMovieDetails
import com.fernandocejas.sample.features.movies.usecase.GetMovies
import com.fernandocejas.sample.features.movies.usecase.PlayMovie
import com.fernandocejas.sample.features.movies.view.MovieDetailsAnimator
import com.fernandocejas.sample.features.movies.view.MoviesAdapter
import com.fernandocejas.sample.features.movies.viewmodel.MovieDetailsViewModel
import com.fernandocejas.sample.features.movies.viewmodel.MoviesViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val applicationModule = module {


    single {
        Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture-Kotlin/")
                .client(get()).addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    single {

        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        okHttpClientBuilder.build() as OkHttpClient
    }

    factory { NetworkHandler(get()) }
    factory { MoviesService(get()) }
    single { Navigator(get()) }
}

val loginModule = module {
    single { Authenticator() }
}
val moviesModule = module {
    single { MoviesRepository.Network(get(), get()) as MoviesRepository }
    single { GetMovieDetails(get()) }
    single { GetMovies(get()) }
    single { PlayMovie(get(), get()) }
    single { MoviesAdapter() }
    factory { MovieDetailsAnimator() }
    viewModel { MovieDetailsViewModel(get(), get()) }
    viewModel { MoviesViewModel(get()) }
}
