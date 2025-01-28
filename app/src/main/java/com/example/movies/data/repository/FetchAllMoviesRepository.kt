package com.example.movies.data.repository

import com.example.movies.data.model.MoviesResult

interface FetchAllMoviesRepository {

    suspend fun fetchAllMovies(): MoviesResult

    suspend fun fetchUpcomingMovies(): MoviesResult

    suspend fun fetchTopRatedMovies(): MoviesResult

    suspend fun fetchSimilarMovies(movieId: Int): MoviesResult

    suspend fun searchMovieByTitle(movieTitle: String): MoviesResult
}