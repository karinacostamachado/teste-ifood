package com.example.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.commons.extensions.observeViewStates
import com.example.movies.databinding.SearchMoviesFragmentBinding
import com.example.movies.di.providesNavController
import com.example.movies.navigation.MoviesNavigation
import com.example.movies.ui.adapter.MoviesAdapter
import com.example.movies.ui.viewmodel.SearchMoviesViewModel
import com.example.movies.ui.vo.MovieVO

class SearchMoviesFragment : Fragment(R.layout.search_movies_fragment) {

    private var _binding: SearchMoviesFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SearchMoviesViewModel by viewModels {
        SearchMoviesViewModel.instance
    }

    private val navigation: MoviesNavigation by lazy { providesNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearchViewListener()
        onBackButtonPressed()
        showEmptyListLayout()
    }

    private fun onBackButtonPressed() {
        binding.appBar.backButton.setOnClickListener {
            navigation.popStackBack()
        }
    }

    private fun setupSearchViewListener() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do nothing on submit
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    hideRecyclerView()
                    showEmptyListLayout()
                } else {
                    observeMoviesResult(newText)
                }
                return true
            }
        })
    }

    private fun observeMoviesResult(movieTitle: String) {
        viewModel.searchMoviesByTitle(movieTitle)
        viewModel.moviesResult.observeViewStates(viewLifecycleOwner,
            onLoading = { showLoading() },
            onSuccess = { movies ->
                hideLoading()
                hideEmptyListLayout()
                setupRecyclerView(movies)
            },
            onEmpty = {
                hideLoading()
                showEmptyListLayout()
                hideRecyclerView()
            },
            onError = {
                hideLoading()
                showError()
                hideRecyclerView()
            })
    }

    private fun showError() {
        navigation.SearchMoviesFragmentNavigation().goToGenericError()
    }

    private fun showLoading() {
        binding.shimmer.startShimmer()
        binding.shimmer.visibility = View.VISIBLE
    }

    private fun showEmptyListLayout() {
        binding.emptyMovieListContainer.visibility = View.VISIBLE
    }

    private fun hideEmptyListLayout() {
        binding.emptyMovieListContainer.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.shimmer.stopShimmer()
        binding.shimmer.visibility = View.GONE
    }

    private fun setupRecyclerView(movies: List<MovieVO>?) {
        with(binding.searchResultsRecyclerView) {
            movies?.let {
                adapter = MoviesAdapter(movies) { movie ->
                    navigation.SearchMoviesFragmentNavigation().goToMovieDetail(movie)
                }
                layoutManager =
                    GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                visibility = View.VISIBLE
            }
        }
    }

    private fun hideRecyclerView() {
        binding.searchResultsRecyclerView.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
