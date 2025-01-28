package com.example.movies.commons.extensions

import java.text.SimpleDateFormat
import java.util.Locale

fun String.formatDate(): String {
    if (this.isBlank()) {
        return ""
    }

    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    return try {
        val date = inputFormat.parse(this) ?: return "Invalid Date"
        outputFormat.format(date)
    } catch (e: Exception) {
        "Invalid Date"
    }
}
