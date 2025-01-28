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
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesListViewModel(
    private val fetchAllMoviesRepository: FetchAllMoviesRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _movies = MutableLiveData<ViewState<List<MovieVO>>>()
    val movies: LiveData<ViewState<List<MovieVO>>> = _movies

    private val _upcomingMovies = MutableLiveData<ViewState<List<MovieVO>>>()
    val upcomingMovies: LiveData<ViewState<List<MovieVO>>> = _upcomingMovies

    private val _topRatedMovies = MutableLiveData<ViewState<List<MovieVO>>>()
    val topRatedMovies: LiveData<ViewState<List<MovieVO>>> = _topRatedMovies

    fun fetchMovies() {
        viewModelScope.launch(dispatcher) {
            val movies = async { fetchAllMoviesRepository.fetchAllMovies() }
            val upcomingMovies = async { fetchAllMoviesRepository.fetchUpcomingMovies() }
            val topRatedMovies = async { fetchAllMoviesRepository.fetchTopRatedMovies() }

            try {
                val moviesResult = movies.await()
                val upcomingMoviesResult = upcomingMovies.await()
                val topRatedMoviesResult = topRatedMovies.await()

                _movies.postValue(ViewState.Success(MoviesMapper.map(moviesResult)))
                _upcomingMovies.postValue(ViewState.Success(MoviesMapper.map(upcomingMoviesResult)))
                _topRatedMovies.postValue(ViewState.Success(MoviesMapper.map(topRatedMoviesResult)))
            } catch (e: Exception) {
                handleError(e)
            }
        }
    }

    fun handleError(e: Exception) {
        val errorMessage = e.message.toString()
        _movies.postValue(ViewState.Error(errorMessage))
        _upcomingMovies.postValue(ViewState.Error(errorMessage))
        _topRatedMovies.postValue(ViewState.Error(errorMessage))
    }

    companion object {
        val instance = viewModelFactory {
            initializer {
                MoviesListViewModel(providesFetchAllMoviesRepository())
            }
        }
    }
}
