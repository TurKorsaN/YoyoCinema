package com.alpersevindik.yoyocinema.data.repository.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alpersevindik.yoyocinema.data.model.MovieDetail
import com.alpersevindik.yoyocinema.data.repository.local.dao.FavoriteDao

@Database(
        entities = [MovieDetail::class],
        version = 1)
abstract class YoyoDatabase : RoomDatabase() {

    abstract fun getFavoriteDao(): FavoriteDao

    companion object {
        private var INSTANCE: YoyoDatabase? = null

        fun getInstance(context: Context): YoyoDatabase? {
            if (INSTANCE == null) {
                synchronized(YoyoDatabase::class) {
                    INSTANCE = Room
                            .databaseBuilder(context.applicationContext
                                    , YoyoDatabase::class.java
                                    , "yoyo.db")
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}