package com.example.movies.mapper

import com.example.movies.commons.BaseTest
import com.example.movies.data.model.MoviesResult
import com.example.movies.ui.vo.MovieVO
import io.mockk.unmockkObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MoviesMapperTest : BaseTest() {

    private lateinit var mapper: Mapper<MoviesResult, MovieVO>

    @Before
    fun setUp() {
        mapper = MoviesMapper
    }

    @After
    override fun tearDown() {
        super.tearDown()
        unmockkObject(mapper)
    }

    @Test
    fun `map should return a mapped MovieVO list`() {
        val mappedMoviesList = mapper.map(moviesList)

        assertEquals(moviesVOList, mappedMoviesList)
    }

    @Test
    fun `map should return an empty list when the input is an empty list`() {
        val mappedMoviesList = mapper.map(emptyMoviesList)

        val expectedList = emptyList<MovieVO>()

        assertEquals(expectedList, mappedMoviesList)
    }

}