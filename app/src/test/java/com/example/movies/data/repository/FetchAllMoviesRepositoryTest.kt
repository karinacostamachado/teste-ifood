package com.example.movies.data.repository

import com.example.movies.data.api.MoviesApi
import com.example.movies.commons.BaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchAllMoviesRepositoryTest : BaseTest() {

    private val api = mockk<MoviesApi>(relaxed = true)
    private lateinit var repository: FetchAllMoviesRepository

    @Before
    fun setUp() {
        repository = FetchAllMoviesRepositoryImpl(api)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `fetchAllMovies should return an empty list of movies`() = runBlocking {
        coEvery { api.fetchMovies(language = any(), authHeader = any()) } returns emptyMoviesList

        val movies = repository.fetchAllMovies()

        assertEquals(emptyMoviesList, movies)
    }

    @Test
    fun `fetchAllMovies should return a populated list of movies`() = runBlocking {
        coEvery { api.fetchMovies(language = any(), authHeader = any()) } returns moviesList

        val movies = repository.fetchAllMovies()

        assertEquals(moviesList, movies)
    }

    @Test
    fun `fetchUpcomingMovies should return an empty list of movies`() = runBlocking {
        coEvery {
            api.fetchUpcomingMovies(
                language = any(), authHeader = any()
            )
        } returns emptyMoviesList

        val movies = repository.fetchUpcomingMovies()

        assertEquals(emptyMoviesList, movies)
    }

    @Test
    fun `fetchUpcomingMovies should return a populated list of movies`() = runBlocking {
        coEvery { api.fetchUpcomingMovies(language = any(), authHeader = any()) } returns moviesList

        val movies = repository.fetchUpcomingMovies()

        assertEquals(moviesList, movies)
    }

    @Test
    fun `fetchTopRatedMovies should return an empty list of movies`() = runBlocking {
        coEvery {
            api.fetchTopRatedMovies(
                language = any(), authHeader = any()
            )
        } returns emptyMoviesList

        val movies = repository.fetchTopRatedMovies()

        assertEquals(emptyMoviesList, movies)
    }

    @Test
    fun `fetchTopRatedMovies should return a populated list of movies`() = runBlocking {
        coEvery { api.fetchTopRatedMovies(language = any(), authHeader = any()) } returns moviesList

        val movies = repository.fetchTopRatedMovies()

        assertEquals(moviesList, movies)
    }

    @Test
    fun `fetchSimilarMovies should return an empty list of movies`() = runBlocking {
        val movieId = 0

        coEvery {
            api.fetchSimilarMovies(
                language = any(), authHeader = any(), movieId = movieId
            )
        } returns emptyMoviesList

        val movies = repository.fetchSimilarMovies(movieId)

        assertEquals(emptyMoviesList, movies)
    }

    @Test
    fun `fetchSimilarMovies should return a populated list of movies`() = runBlocking {
        val movieId = 0

        coEvery {
            api.fetchSimilarMovies(
                language = any(), authHeader = any(), movieId = movieId
            )
        } returns moviesList

        val movies = repository.fetchSimilarMovies(movieId)

        assertEquals(moviesList, movies)
    }

    @Test
    fun `searchMovieByTitle should return an empty list of movies`() = runBlocking {
        val movieTitle = "Movie title"

        coEvery {
            api.searchMoviesByTitle(
                language = any(), authHeader = any(), movieTitle = movieTitle
            )
        } returns emptyMoviesList

        val movies = repository.searchMovieByTitle(movieTitle)

        assertEquals(emptyMoviesList, movies)
    }

    @Test
    fun `searchMovieByTitle should return a populated list of movies`() = runBlocking {
        val movieTitle = "Movie Title"

        coEvery {
            api.searchMoviesByTitle(
                language = any(), authHeader = any(), movieTitle = movieTitle
            )
        } returns moviesList

        val movies = repository.searchMovieByTitle(movieTitle)

        assertEquals(moviesList, movies)
    }
}
