package com.example.movies.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesResult (
    @SerializedName("results")
    val results: List<Movie>
): Parcelable