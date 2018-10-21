package com.alpersevindik.yoyocinema.data.model

data class SearchMovie(
        val page: Int = 0,
        val total_results: Int = 0,
        val total_pages: Int = 0,
        val results: List<Movie> = emptyList()
)