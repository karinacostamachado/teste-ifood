package com.example.movies.navigation

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.example.movies.ui.fragment.MovieDetailFragmentDirections
import com.example.movies.ui.fragment.MoviesListFragmentDirections
import com.example.movies.ui.fragment.SearchMoviesFragmentDirections
import com.example.movies.ui.vo.MovieVO

class MoviesNavigation(
    private val navController: NavController
) {

    inner class MoviesListFragmentNavigation {
        fun goToMovieDetail(movie: MovieVO) {
            navigateTo(
                MoviesListFragmentDirections.goToMovieDetail(movie)
            )
        }

        fun goToSearchMovies() {
            navigateTo(
                MoviesListFragmentDirections.goToSearchMoviesFragment()
            )
        }

        fun goToGenericError() {
            navigateTo(
                MoviesListFragmentDirections.goToGenericErrorFragment()
            )
        }
    }

    inner class MovieDetailFragmentNavigation {
        fun goToMoviesDetail(movie: MovieVO) {
            navigateTo(
                MovieDetailFragmentDirections.goToMovieDetail(movie)
            )
        }

        fun goToGenericError() {
            navigateTo(
                MovieDetailFragmentDirections.goToGenericErrorFragment()
            )
        }
    }

    inner class SearchMoviesFragmentNavigation {
        fun goToMovieDetail(movie: MovieVO) {
            navigateTo(
                SearchMoviesFragmentDirections.goToMovieDetail(movie)
            )
        }

        fun goToGenericError() {
            navigateTo(
                SearchMoviesFragmentDirections.goToGenericErrorFragment()
            )
        }
    }

    private fun navigateTo(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    fun popStackBack() {
        navController.popBackStack()
    }
}