package com.example.movies.di

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.movies.navigation.MoviesNavigation

internal fun providesNavController(fragment: Fragment) =
    MoviesNavigation(fragment.findNavController())