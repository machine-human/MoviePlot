package com.mhuman.movieplot.data.local.favorite

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mhuman.movieplot.network.model.MovieInfoList

@Database(entities = [MovieInfoList::class], version = 1)
abstract class FavoriteMovieDataBase : RoomDatabase() {
    abstract fun FavoriteMovieDao(): FavoriteMovieDao
}