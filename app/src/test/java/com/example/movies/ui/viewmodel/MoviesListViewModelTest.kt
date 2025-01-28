import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.movies.commons.ViewState
import com.example.movies.commons.BaseTest
import com.example.movies.data.repository.FetchAllMoviesRepository
import com.example.movies.ui.viewmodel.MoviesListViewModel
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class MoviesListViewModelTest : BaseTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val fetchAllMoviesRepository: FetchAllMoviesRepository = mockk(relaxed = true)
    private val dispatcher = UnconfinedTestDispatcher()
    private val viewModel = MoviesListViewModel(fetchAllMoviesRepository, dispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    override fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchMovies should update live data when repository returns data`() =
        runTest(dispatcher) {

            coEvery { fetchAllMoviesRepository.fetchAllMovies() } returns moviesList
            coEvery { fetchAllMoviesRepository.fetchUpcomingMovies() } returns moviesList
            coEvery { fetchAllMoviesRepository.fetchTopRatedMovies() } returns moviesList

            val moviesObserver: Observer<ViewState<List<MovieVO>>> = mockk(relaxed = true)
            val upcomingMoviesObserver: Observer<ViewState<List<MovieVO>>> = mockk(relaxed = true)
            val topRatedMoviesObserver: Observer<ViewState<List<MovieVO>>> = mockk(relaxed = true)

            viewModel.movies.observeForever(moviesObserver)
            viewModel.upcomingMovies.observeForever(upcomingMoviesObserver)
            viewModel.topRatedMovies.observeForever(topRatedMoviesObserver)

            viewModel.fetchMovies()

            coVerify { fetchAllMoviesRepository.fetchAllMovies() }
            coVerify { fetchAllMoviesRepository.fetchUpcomingMovies() }
            coVerify { fetchAllMoviesRepository.fetchTopRatedMovies() }

            verify { moviesObserver.onChanged(any()) }
            verify { upcomingMoviesObserver.onChanged(any()) }
            verify { topRatedMoviesObserver.onChanged(any()) }
        }

    @Test
    fun `test handleError updates LiveData with error state`() {
        val exceptionMessage = "Erro de rede"
        val exception = Exception(exceptionMessage)

        val errorObserver: Observer<ViewState<List<MovieVO>>> = mockk(relaxed = true)

        viewModel.movies.observeForever(errorObserver)
        viewModel.upcomingMovies.observeForever(errorObserver)
        viewModel.topRatedMovies.observeForever(errorObserver)

        viewModel.handleError(exception)

        verify {
            errorObserver.onChanged(ViewState.Error(exceptionMessage))
        }
    }
}
