package com.example.movies.data.model

import android.os.Parcelable
import com.example.movies.BuildConfig
import com.example.movies.commons.extensions.formatDate
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @SerializedName("adult")
    val isAdult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genresId: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
) : Parcelable {

    companion object {
        fun getReleaseDate(releaseDate: String): String = releaseDate.formatDate()

        fun getPosterPath(posterPath: String?) = BuildConfig.IMAGE_URL + posterPath
    }
}
