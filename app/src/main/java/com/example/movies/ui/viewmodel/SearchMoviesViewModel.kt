package com.example.movies.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.movies.commons.ViewState
import com.example.movies.data.repository.FetchAllMoviesRepository
import com.example.movies.di.providesFetchAllMoviesRepository
import com.example.movies.mapper.MoviesMapper
import com.example.movies.ui.vo.MovieVO
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchMoviesViewModel(
    private val repository: FetchAllMoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _moviesResult = MutableLiveData<ViewState<List<MovieVO>>>()
    val moviesResult: LiveData<ViewState<List<MovieVO>>> = _moviesResult

    fun searchMoviesByTitle(movieTitle: String) {
        viewModelScope.launch(dispatcher) {
            _moviesResult.postValue(ViewState.Loading)

            try {
                val filteredMovies = repository.searchMovieByTitle(movieTitle)
                val mappedMovies = MoviesMapper.map(filteredMovies)

                if (filteredMovies.results.isEmpty()) {
                    _moviesResult.postValue(ViewState.Empty)
                    return@launch
                }

                _moviesResult.postValue(ViewState.Success(mappedMovies))
            } catch (e: Exception) {
                _moviesResult.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }

    companion object {
        val instance = viewModelFactory {
            initializer {
                SearchMoviesViewModel(providesFetchAllMoviesRepository())
            }
        }
    }
}