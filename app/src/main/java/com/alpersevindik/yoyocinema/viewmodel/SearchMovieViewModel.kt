package com.alpersevindik.yoyocinema.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.alpersevindik.yoyocinema.data.model.Movie
import com.alpersevindik.yoyocinema.data.repository.network.datasource.SearchRecipeNetworkDataSource

class SearchMovieViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var searchCache: LiveData<PagedList<Movie>>
    private var lastQuery: String = ""

    fun search(query: String): LiveData<PagedList<Movie>> {
        if (query != lastQuery || !::searchCache.isInitialized)
            searchCache = LivePagedListBuilder(SearchRecipeNetworkDataSource.Factory(query.replace(" ","+")), 20).build()
        lastQuery = query
        return searchCache
    }

}