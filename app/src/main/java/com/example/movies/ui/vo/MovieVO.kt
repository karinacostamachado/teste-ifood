package com.example.movies.ui.vo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieVO(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val overview: String,
): Parcelable
