package com.example.movies.commons.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.example.movies.commons.ViewState

fun <T> LiveData<ViewState<T>>.observeViewStates(
    owner: LifecycleOwner,
    onLoading: () -> Unit,
    onSuccess: (T?) -> Unit,
    onEmpty: () -> Unit = {},
    onError: (String) -> Unit
) {
    this.observe(owner) { viewState ->
        when (viewState) {
            is ViewState.Loading -> {
                onLoading()
            }

            is ViewState.Success -> {
                onSuccess(viewState.data)
            }

            is ViewState.Empty -> {
                onEmpty()
            }

            is ViewState.Error -> {
                onError(viewState.message)
            }
        }
    }
}
