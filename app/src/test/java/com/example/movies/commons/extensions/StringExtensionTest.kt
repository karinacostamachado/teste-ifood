package com.example.movies.commons.extensions

import org.junit.Assert.assertEquals
import org.junit.Test

class StringFormatDateTest {

    @Test
    fun `formatDate should return formatted date string when input is valid`() {
        val inputDate = "2023-10-27"
        val expectedOutput = "27-10-2023"

        val actualOutput = inputDate.formatDate()

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `formatDate should return empty string when input is blank`() {
        val inputDate = ""
        val expectedOutput = ""

        val actualOutput = inputDate.formatDate()

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `formatDate should return Invalid Date when input is invalid format`() {
        val inputDate = "2023/10/27"
        val expectedOutput = "Invalid Date"

        val actualOutput = inputDate.formatDate()

        assertEquals(expectedOutput, actualOutput)
    }

    @Test
    fun `formatDate should return Invalid Date when input is not a date`() {
        val inputDate = "not a date"
        val expectedOutput = "Invalid Date"

        val actualOutput = inputDate.formatDate()

        assertEquals(expectedOutput, actualOutput)
    }
}