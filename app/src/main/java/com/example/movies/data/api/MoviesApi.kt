package com.example.movies.data.api

import com.example.movies.data.model.MoviesResult
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("discover/movie")
    suspend fun fetchMovies(
        @Query("include_adult") includeAdult: Boolean = false,
        @Query("include_video") includeVideo: Boolean = false,
        @Query("language") language: String,
        @Query("page") page: Int = 1,
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Header("Authorization") authHeader: String
    ): MoviesResult


    @GET("movie/upcoming")
    suspend fun fetchUpcomingMovies(
        @Query("language") language: String,
        @Header("Authorization") authHeader: String
    ): MoviesResult

    @GET("movie/top_rated")
    suspend fun fetchTopRatedMovies(
        @Query("language") language: String,
        @Header("Authorization") authHeader: String
    ): MoviesResult

    @GET("movie/{movie_id}/similar")
    suspend fun fetchSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Header("Authorization") authHeader: String
    ): MoviesResult

    @GET("search/movie")
    suspend fun searchMoviesByTitle(
        @Query("query") movieTitle: String,
        @Query("language") language: String,
        @Header("Authorization") authHeader: String
    ): MoviesResult
}
