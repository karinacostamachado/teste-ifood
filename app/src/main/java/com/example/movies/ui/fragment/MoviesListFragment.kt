package com.example.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.commons.extensions.observeViewStates
import com.example.movies.databinding.MoviesListFragmentBinding
import com.example.movies.di.providesNavController
import com.example.movies.ui.adapter.MoviesAdapter
import com.example.movies.ui.viewmodel.MoviesListViewModel
import com.example.movies.ui.vo.MovieVO

class MoviesListFragment : Fragment(R.layout.movies_list_fragment) {

    private var _binding: MoviesListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModel.instance
    }

    private val navigation by lazy { providesNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MoviesListFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        navigateToSearchMovies()
    }

    private fun observeViewModel() {
        viewModel.fetchMovies()
        observeAllMovies()
        observeUpcomingMovies()
        observeTopRatedMovies()
    }

    private fun observeAllMovies() {
        viewModel.movies.observeViewStates(viewLifecycleOwner,
            onLoading = { showLoading() },
            onSuccess = {
                setupMoviesCategory(
                    binding.allMoviesContainer.moviesCategory,
                    getString(R.string.movies_category)
                )
                setupRecyclerView(it, binding.allMoviesContainer.moviesRv)
            },
            onError = { showError() }
        )
    }

    private fun observeUpcomingMovies() {
        viewModel.upcomingMovies.observeViewStates(viewLifecycleOwner,
            onLoading = { showLoading() },
            onSuccess = {
                setupMoviesCategory(
                    binding.upcomingMoviesContainer.moviesCategory,
                    getString(R.string.upcoming_movies_category)
                )
                setupRecyclerView(it, binding.upcomingMoviesContainer.moviesRv)
            },
            onError = { showError() })
    }

    private fun observeTopRatedMovies() {
        viewModel.topRatedMovies.observeViewStates(viewLifecycleOwner,
            onLoading = { showLoading() },
            onSuccess = {
                setupMoviesCategory(
                    binding.topRatedMoviesContainer.moviesCategory,
                    getString(R.string.top_rated_movies_category)
                )
                setupRecyclerView(it, binding.topRatedMoviesContainer.moviesRv)
            },
            onError = { showError() })
    }

    private fun showLoading() {
        with(binding) {
            shimmer.visibility = View.VISIBLE
            shimmer.startShimmer()
            moviesListContainer.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        with(binding) {
            shimmer.visibility = View.GONE
            shimmer.stopShimmer()
            moviesListContainer.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        hideLoading()
        navigation
            .MoviesListFragmentNavigation()
            .goToGenericError()
    }

    private fun setupMoviesCategory(movieTextView: TextView, category: String) {
        movieTextView.text = category
    }

    private fun setupRecyclerView(movies: List<MovieVO>?, recyclerView: RecyclerView) {
        with(recyclerView) {
            movies?.let { moviesList ->
                adapter = MoviesAdapter(moviesList) { movie ->
                    navigation
                        .MoviesListFragmentNavigation()
                        .goToMovieDetail(movie)
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                hideLoading()
            }
        }
    }

    private fun navigateToSearchMovies() {
        binding.searchBarButton.setOnClickListener {
            navigation
                .MoviesListFragmentNavigation()
                .goToSearchMovies()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
