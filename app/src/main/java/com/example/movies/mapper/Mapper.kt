package com.example.movies.mapper

interface Mapper<in T, out I> {

    fun map(moviesResult: T): List<I>
}
