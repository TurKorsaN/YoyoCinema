package com.alpersevindik.yoyocinema.viewmodel

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alpersevindik.yoyocinema.data.model.MovieDetail
import com.alpersevindik.yoyocinema.data.repository.local.YoyoDatabase
import com.alpersevindik.yoyocinema.data.repository.network.ServiceHelper
import com.alpersevindik.yoyocinema.data.repository.network.enqueue
import kotlinx.coroutines.experimental.CoroutineStart
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var cache: LiveData<MovieDetail>

    var isFavorite: ObservableBoolean = object : ObservableBoolean() {
        override fun set(value: Boolean) {
            super.set(value)
            if (value)
                addToFavorites()
            else
                removeFromFavorites()

        }
    }

    fun getDetail(id: Int): LiveData<MovieDetail> {
        if (!::cache.isInitialized)
            cache = ServiceHelper.getInstance().getMovieDetail(id).enqueue().data
        return cache
    }

    fun addToFavorites() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            cache.value?.let { movieDetail ->
                YoyoDatabase
                        .getInstance(getApplication())
                        ?.getFavoriteDao()
                        ?.insert(movieDetail)
            }
        })
    }

    fun removeFromFavorites() {
        GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
            cache.value?.let { movieDetail ->
                YoyoDatabase
                        .getInstance(getApplication())
                        ?.getFavoriteDao()
                        ?.delete(movieDetail.id)
            }
        })
    }

    fun checkFavorites() {
        cache.value?.let { movieDetail ->
            GlobalScope.launch(Dispatchers.Default, CoroutineStart.DEFAULT, null, {
                val favorite = YoyoDatabase
                        .getInstance(getApplication())
                        ?.getFavoriteDao()
                        ?.getFavorite(movieDetail.id)
                favorite?.let {
                    isFavorite.set(true)
                }
            })
        }
    }

}