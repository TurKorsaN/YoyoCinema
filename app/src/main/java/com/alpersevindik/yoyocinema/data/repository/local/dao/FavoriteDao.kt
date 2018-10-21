package com.alpersevindik.yoyocinema.data.repository.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alpersevindik.yoyocinema.data.model.MovieDetail

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites WHERE id = :movieId")
    fun getFavorite(movieId: Int): MovieDetail

    @Query("SELECT * FROM favorites")
    fun getFavorites(): LiveData<List<MovieDetail>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movie: MovieDetail)

    @Query("DELETE FROM favorites WHERE id = :movieId")
    fun delete(movieId: Int)

}