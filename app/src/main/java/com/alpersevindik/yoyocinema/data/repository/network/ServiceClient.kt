package com.alpersevindik.yoyocinema.data.repository.network

import com.alpersevindik.yoyocinema.data.model.MovieDetail
import com.alpersevindik.yoyocinema.data.model.SearchMovie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceClient {

    @GET(ServiceURL.SEARCH)
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int? = null): Call<SearchMovie>

    @GET(ServiceURL.MOVIE_DETAIL)
    fun getMovieDetail(@Path("id") id: Int): Call<MovieDetail>
}