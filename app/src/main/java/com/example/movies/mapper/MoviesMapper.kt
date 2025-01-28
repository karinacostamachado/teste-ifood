package com.example.movies.mapper

import com.example.movies.data.model.Movie.Companion.getPosterPath
import com.example.movies.data.model.Movie.Companion.getReleaseDate
import com.example.movies.data.model.MoviesResult
import com.example.movies.ui.vo.MovieVO

object MoviesMapper : Mapper<MoviesResult, MovieVO> {

    override fun map(moviesResult: MoviesResult): List<MovieVO> {
        val mappedMovies = moviesResult.results.map {
            MovieVO(
                id = it.id,
                title = it.title,
                overview = it.overview,
                posterPath = getPosterPath(it.posterPath),
                releaseDate = getReleaseDate(it.releaseDate),
                voteAverage = it.voteAverage,
            )
        }

        return mappedMovies
    }
}
