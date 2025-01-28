package com.example.movies.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.movies.commons.ViewState
import com.example.movies.commons.BaseTest
import com.example.movies.data.repository.FetchAllMoviesRepository
import com.example.movies.ui.vo.MovieVO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovieDetailViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val observer = mockk<Observer<ViewState<List<MovieVO>>>>(relaxed = true)

    private val fetchAllMoviesRepository = mockk<FetchAllMoviesRepository>(relaxed = true)
    private lateinit var viewModel: MovieDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    private val movieId = 123

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = MovieDetailViewModel(fetchAllMoviesRepository, testDispatcher)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchSimilarMovies should return success when repository returns data`() =
        runTest(testDispatcher) {
            val currentData = viewModel.similarMovies

            coEvery { fetchAllMoviesRepository.fetchSimilarMovies(movieId) } returns moviesList

            currentData.observeForever(observer)
            viewModel.fetchSimilarMovies(movieId)

            coVerify { fetchAllMoviesRepository.fetchSimilarMovies(movieId) }
            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Success }) }

            val successState = viewModel.similarMovies.value as ViewState.Success<List<MovieVO>>

            assertEquals(1, successState.data.size)

            removeObserver(currentData)
        }

    @Test
    fun `fetchSimilarMovies should return empty when repository returns empty data`() =
        runTest(testDispatcher) {
            coEvery { fetchAllMoviesRepository.fetchSimilarMovies(movieId) } returns emptyMoviesList

            viewModel.similarMovies.observeForever(observer)

            viewModel.fetchSimilarMovies(movieId)

            coVerify { fetchAllMoviesRepository.fetchSimilarMovies(movieId) }
            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Empty }) }

            val emptyState = viewModel.similarMovies.value as ViewState.Empty

            assertEquals(ViewState.Empty, emptyState)

            removeObserver(viewModel.similarMovies)
        }

    @Test
    fun `fetchSimilarMovies should return error when repository throws exception`() =
        runTest(testDispatcher) {
            val errorMessage = "Network error"
            coEvery { fetchAllMoviesRepository.fetchSimilarMovies(movieId) } throws Exception(
                errorMessage
            )

            viewModel.similarMovies.observeForever(observer)

            viewModel.fetchSimilarMovies(movieId)

            coVerify { fetchAllMoviesRepository.fetchSimilarMovies(movieId) }
            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Error }) }

            val errorState = viewModel.similarMovies.value as ViewState.Error

            assertEquals(errorMessage, errorState.message)

            viewModel.similarMovies.removeObserver(observer)
        }

    private fun removeObserver(currentData: LiveData<ViewState<List<MovieVO>>>) {
        currentData.removeObserver(observer)
    }
}