package com.example.movies.data.repository

import com.example.movies.BuildConfig
import com.example.movies.data.api.MoviesApi
import com.example.movies.data.model.MoviesResult

class FetchAllMoviesRepositoryImpl(
    private val api: MoviesApi
) : FetchAllMoviesRepository {

    override suspend fun fetchAllMovies(): MoviesResult {
        return withCommonParameters { authHeader, language ->
            api.fetchMovies(authHeader = authHeader, language = language)
        }
    }

    override suspend fun fetchUpcomingMovies(): MoviesResult {
        return withCommonParameters { authHeader, language ->
            api.fetchUpcomingMovies(authHeader = authHeader, language = language)
        }
    }

    override suspend fun fetchTopRatedMovies(): MoviesResult {
        return withCommonParameters { authHeader, language ->
            api.fetchTopRatedMovies(authHeader = authHeader, language = language)
        }
    }

    override suspend fun fetchSimilarMovies(movieId: Int): MoviesResult {
        return withCommonParameters { authHeader, language ->
            api.fetchSimilarMovies(movieId = movieId, language = language, authHeader = authHeader)
        }
    }

    override suspend fun searchMovieByTitle(movieTitle: String): MoviesResult {
        return withCommonParameters { authHeader, language ->
            api.searchMoviesByTitle(
                movieTitle = movieTitle,
                language = language,
                authHeader = authHeader
            )
        }
    }

    private suspend fun <T> withCommonParameters(block: suspend (authHeader: String, language: String) -> T): T {
        return block(BuildConfig.API_KEY, "pt-br")
    }
}
