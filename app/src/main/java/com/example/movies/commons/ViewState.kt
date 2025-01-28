package com.example.movies.commons

sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()
    data object Empty : ViewState<Nothing>()
    data class Error(val message: String) : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
}
