package com.example.movies.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movies.R
import com.example.movies.commons.extensions.observeViewStates
import com.example.movies.commons.loadImage
import com.example.movies.databinding.MovieDetailFragmentBinding
import com.example.movies.di.providesNavController
import com.example.movies.ui.adapter.MoviesAdapter
import com.example.movies.ui.viewmodel.MovieDetailViewModel
import com.example.movies.ui.vo.MovieVO
import kotlin.math.roundToInt

class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment) {

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding get() = _binding!!

    private val navigation by lazy { providesNavController(this) }
    private val navArguments: MovieDetailFragmentArgs by navArgs()

    private val viewModel: MovieDetailViewModel by viewModels {
        MovieDetailViewModel.instance
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        onBackButtonPressed()
        observeViewModel()
    }

    private fun setupLayout() {
        with(binding) {
            loadImage(
                requireContext(),
                navArguments.movie.posterPath,
                binding.movieImg
            )
            setOverviewVisibility(navArguments.movie.overview)
            title.text = navArguments.movie.title
            releaseDate.text = navArguments.movie.releaseDate
            voteAverage.progress = navArguments.movie.voteAverage.roundToInt()

        }
    }

    private fun setOverviewVisibility(overviewText: String) {
        if (overviewText.isBlank()) {
            binding.movieOverviewTitle.visibility = View.GONE
            binding.movieOverview.visibility = View.GONE
        } else {
            binding.movieOverview.text = overviewText
            binding.movieOverview.visibility = View.VISIBLE
        }
    }

    private fun onBackButtonPressed() {
        binding.movieDetailAppBar.backButton.setOnClickListener {
            navigation
                .popStackBack()
        }
    }

    private fun observeViewModel() {
        viewModel.fetchSimilarMovies(navArguments.movie.id)
        viewModel.similarMovies.observeViewStates(viewLifecycleOwner,
            onLoading = { showLoading() },
            onSuccess = { setupRecyclerView(it) },
            onEmpty = { hideSimilarMovies() },
            onError = { showError() })
    }


    private fun hideSimilarMovies() {
        hideLoading()
        binding.similarMoviesRv.visibility = View.GONE
        binding.similarMoviesTitle.visibility = View.GONE
    }

    private fun showLoading() {
        binding.shimmer.startShimmer()
    }

    private fun hideLoading() {
        with(binding) {
            shimmer.stopShimmer()
            shimmer.visibility = View.GONE
            movieDetailContainer.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        hideLoading()
        navigation
            .MovieDetailFragmentNavigation()
            .goToGenericError()
    }

    private fun setupRecyclerView(moviesList: List<MovieVO>?) {
        binding.similarMoviesRv.apply {
            moviesList?.let {
                adapter = MoviesAdapter(moviesList) {
                    navigation
                        .MovieDetailFragmentNavigation()
                        .goToMoviesDetail(movie = it)
                }
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                hideLoading()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
