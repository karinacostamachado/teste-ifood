package com.example.movies.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.commons.loadImage
import com.example.movies.databinding.MovieItemBinding
import com.example.movies.ui.vo.MovieVO

class MoviesAdapter(
    private val moviesList: List<MovieVO>,
    private val onMovieClicked: (MovieVO) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemViewBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(
            itemViewBinding
        )
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(moviesList[position])
    }

    inner class ViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieVO) {
            loadImage(
                binding.movieImg.context,
                movie.posterPath,
                binding.movieImg
            )
            binding.titleTv.text = movie.title
            binding.releaseDate.text = movie.releaseDate

            binding.root.setOnClickListener {
                onMovieClicked(movie)
            }
        }
    }
}
