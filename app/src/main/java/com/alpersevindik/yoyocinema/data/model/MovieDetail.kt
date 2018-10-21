package com.alpersevindik.yoyocinema.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.alpersevindik.yoyocinema.helper.diffutil.IDiffUtilModel

@Entity(tableName = "favorites")
data class MovieDetail(
        @PrimaryKey
        var id: Int = 0,
        var adult: Boolean = false,
        var backdrop_path: String? = "",
        @Ignore
        var belongs_to_collection: MovieCollection? = null,
        var budget: Int = 0,
        @Ignore
        var genres: List<Genre> = emptyList(),
        var homepage: String? = null,
        var imdb_id: String? = "",
        var original_language: String = "",
        var original_title: String = "",
        var overview: String = "",
        var popularity: Float = 0f,
        var poster_path: String? = "",
        @Ignore
        var production_companies: List<ProductionCompany> = emptyList(),
        @Ignore
        var production_countries: List<ProductionCountry> = emptyList(),
        var release_date: String = "",
        var revenue: Int = 0,
        var runtime: Int = 60,
        @Ignore
        var spoken_languages: List<SpokenLanguage>? = emptyList(),
        var status: String = "",
        var tagline: String = "",
        var title: String = "",
        var video: Boolean = true,
        var vote_average: Float = 0f,
        var vote_count: Int = 0
) : IDiffUtilModel {
    override var diffId: Int = 0
        get() = id
}

data class MovieCollection(
        val id: Int = 0,
        val name: String = "",
        val poster_path: String = "",
        val backdrop_path: String = ""
)

data class Genre(
        val id: Int = 0,
        val name: String = ""
)

data class ProductionCompany(
        val id: Int = 0,
        val logo_path: String = "",
        val name: String = "",
        val origin_country: String = ""
)

data class ProductionCountry(
        val iso_3166_1: String = "",
        val name: String = ""
)

data class SpokenLanguage(
        val iso_639_1: String = "",
        val name: String = ""
)