package com.alpersevindik.yoyocinema.data.repository.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.alpersevindik.yoyocinema.data.model.Movie
import com.alpersevindik.yoyocinema.data.model.SearchMovie
import com.alpersevindik.yoyocinema.data.repository.network.ServiceHelper
import retrofit2.Call
import retrofit2.Response
import java.io.IOException


class SearchRecipeNetworkDataSource(
        private val query: String
) : PageKeyedDataSource<Int, Movie>() {

    private var retry: (() -> Any)? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        val request = ServiceHelper.getInstance().searchMovie(query)
        try {
            val response = request.execute()
            val items = response.body()?.results
            retry = null
            items?.let { callback.onResult(it, null, 2) }
        } catch (ioException: IOException) {
            retry = {
                loadInitial(params, callback)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

        ServiceHelper.getInstance().searchMovie(query, params.key).enqueue(object : retrofit2.Callback<SearchMovie> {
            override fun onFailure(call: Call<SearchMovie>?, t: Throwable?) {
                retry = {
                    loadAfter(params, callback)
                }
            }

            override fun onResponse(call: Call<SearchMovie>?, response: Response<SearchMovie>?) {
                if (response?.isSuccessful == true) {
                    response.body()?.let { result ->
                        val items = result.results
                        val nextPageKey = if (result.total_pages >= params.key + 1) params.key + 1 else null

                        items.let { callback.onResult(it, nextPageKey) }
                    }
                } else {
                    retry = {
                        loadAfter(params, callback)
                    }
                }
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {

    }

    class Factory(
            private val query: String
    ) : DataSource.Factory<Int, Movie>() {
        private val sourceLiveData = MutableLiveData<SearchRecipeNetworkDataSource>()
        override fun create(): DataSource<Int, Movie> {
            val source = SearchRecipeNetworkDataSource(query)
            sourceLiveData.postValue(source)
            return source
        }

    }
}