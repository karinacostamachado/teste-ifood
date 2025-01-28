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

class MovieDetailViewModel(
    private val repository: FetchAllMoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _similarMovies = MutableLiveData<ViewState<List<MovieVO>>>()
    val similarMovies: LiveData<ViewState<List<MovieVO>>> = _similarMovies

    fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch(dispatcher) {
            _similarMovies.postValue(ViewState.Loading)

            try {
                val response = repository.fetchSimilarMovies(movieId)

                if (response.results.isEmpty()) {
                    _similarMovies.postValue(ViewState.Empty)
                    return@launch
                }
                val mappedMovies = MoviesMapper.map(response)

                _similarMovies.postValue(ViewState.Success(mappedMovies))
            } catch (e: Exception) {
                _similarMovies.postValue(ViewState.Error(e.message.toString()))
            }
        }
    }

    companion object {
        val instance = viewModelFactory {
            initializer {
                MovieDetailViewModel(providesFetchAllMoviesRepository())
            }
        }
    }
}
