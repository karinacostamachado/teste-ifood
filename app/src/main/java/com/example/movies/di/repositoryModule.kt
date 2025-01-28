package com.example.movies.di

import com.example.movies.data.repository.FetchAllMoviesRepositoryImpl
import com.example.movies.data.service.MovieFlixApiService

internal fun providesApiInstance() = MovieFlixApiService.createApiInstance()
internal fun providesFetchAllMoviesRepository() = FetchAllMoviesRepositoryImpl(providesApiInstance())