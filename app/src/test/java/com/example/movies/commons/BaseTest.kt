package com.example.movies.commons

import com.example.movies.data.model.Movie
import com.example.movies.data.model.Movie.Companion.getPosterPath
import com.example.movies.data.model.Movie.Companion.getReleaseDate
import com.example.movies.data.model.MoviesResult
import com.example.movies.ui.vo.MovieVO
import io.mockk.unmockkAll
import org.junit.After

open class BaseTest {

    @After
    open fun tearDown() {
        unmockkAll()
    }

    val emptyMoviesList = MoviesResult(
        results = emptyList()
    )

    val moviesList = MoviesResult(
        results = listOf(
            Movie(
                isAdult = false,
                backdropPath = "/backdrop.jpg",
                genresId = listOf(1, 2, 3),
                id = 123,
                originalLanguage = "pt-br",
                originalTitle = "Original Title",
                overview = "Movie overview",
                popularity = 7.8,
                posterPath = "/poster.jpg",
                releaseDate = "2023-10-27",
                title = "Movie Title",
                video = false,
                voteAverage = 8.5,
                voteCount = 1000,
            )
        )
    )

    val moviesVOList = listOf(
        MovieVO(
            id = 123,
            title = "Movie Title",
            overview = "Movie overview",
            posterPath = getPosterPath("/poster.jpg"),
            releaseDate = getReleaseDate("2023-10-27"),
            voteAverage = 8.5,
        )
    )
}