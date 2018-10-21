package com.alpersevindik.yoyocinema.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.alpersevindik.yoyocinema.data.model.MovieDetail
import com.alpersevindik.yoyocinema.data.repository.local.YoyoDatabase

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    fun getFavorites(): LiveData<List<MovieDetail>>? {
        return YoyoDatabase
                .getInstance(getApplication())
                ?.getFavoriteDao()
                ?.getFavorites()
    }
}