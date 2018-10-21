package com.alpersevindik.yoyocinema.data.repository.network

class ServiceURL {
    companion object {
        private const val API_KEY = "4cb1eeab94f45affe2536f2c684a5c9e"

        const val IMAGE_BASE = "https://image.tmdb.org/t/p/"
        const val BASE_URL = "https://api.themoviedb.org/"

        const val SEARCH = "/3/search/movie?api_key=$API_KEY"
        const val MOVIE_DETAIL = "/3/movie/{id}?api_key=$API_KEY"
    }
}