package com.alpersevindik.yoyocinema.data.model

import com.alpersevindik.yoyocinema.helper.diffutil.IDiffUtilModel

data class Movie(
        val id: Int = 0,
        val vote_count: Int = 0,
        val video: Boolean = false,
        val vote_average: Float = 0f,
        val title: String = "",
        val popularity: Float = 0f,
        val poster_path: String = "",
        val original_language: String = "",
        val original_title: String = "",
        val genre_ids: List<Int> = emptyList(),
        val backdrop_path: String = "",
        val adult: Boolean = false,
        val overview: String = "",
        val release_date: String = "1999-11-15"
) : IDiffUtilModel {

    override var diffId: Int = 0
        get() = id
}