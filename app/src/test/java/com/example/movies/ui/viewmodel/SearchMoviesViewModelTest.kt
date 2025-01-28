package com.example.movies.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movies.commons.ViewState
import com.example.movies.commons.BaseTest
import com.example.movies.data.repository.FetchAllMoviesRepository
import com.example.movies.ui.vo.MovieVO
import io.mockk.coEvery
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
class SearchMoviesViewModelTest : BaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = UnconfinedTestDispatcher()

    private val observer = mockk<Observer<ViewState<List<MovieVO>>>>(relaxed = true)

    private val fetchAllMoviesRepository = mockk<FetchAllMoviesRepository>(relaxed = true)
    private lateinit var viewModel: SearchMoviesViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = SearchMoviesViewModel(fetchAllMoviesRepository)
    }

    @After
    override fun tearDown() {
        super.tearDown()
        Dispatchers.resetMain()
    }

    @Test
    fun `searchMoviesByTitle should return success when repository returns data`() =
        runTest(testDispatcher) {
            val movieTitle = "Movie Title"

            coEvery { fetchAllMoviesRepository.searchMovieByTitle(movieTitle) } returns moviesList

            viewModel.moviesResult.observeForever(observer)

            viewModel.searchMoviesByTitle(movieTitle)

            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Success }) }

            val successState = viewModel.moviesResult.value as ViewState.Success<List<MovieVO>>

            assertEquals(1, successState.data.size)

            viewModel.moviesResult.removeObserver(observer)
        }

    @Test
    fun `searchMoviesByTitle should return empty when repository returns empty list`() =
        runTest(testDispatcher) {
            val movieTitle = "Movie Title"

            coEvery { fetchAllMoviesRepository.searchMovieByTitle(movieTitle) } returns emptyMoviesList

            viewModel.moviesResult.observeForever(observer)

            viewModel.searchMoviesByTitle(movieTitle)

            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Empty }) }

            val emptyState = viewModel.moviesResult.value as ViewState.Empty

            assertEquals(ViewState.Empty, emptyState)

            viewModel.moviesResult.removeObserver(observer)
        }

    @Test
    fun `searchMoviesByTitle should return error when repository throws exception`() =
        runTest(testDispatcher) {
            val movieTitle = "Movie Title"
            val errorMessage = "Network error"

            coEvery { fetchAllMoviesRepository.searchMovieByTitle(movieTitle) } throws Exception(
                errorMessage
            )

            viewModel.moviesResult.observeForever(observer)

            viewModel.searchMoviesByTitle(movieTitle)

            verify { observer.onChanged(match { it is ViewState.Loading }) }
            verify { observer.onChanged(match { it is ViewState.Error }) }

            val errorState = viewModel.moviesResult.value as ViewState.Error

            assertEquals(errorMessage, errorState.message)

            viewModel.moviesResult.removeObserver(observer)
        }
}