package com.mhuman.movieplot.data.local.favorite

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhuman.movieplot.network.model.MovieInfoList
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovieInfo(movieInfoList: List<MovieInfoList>): Completable

    @Query("SELECT * FROM movieInfo")
    fun getAllFavoriteMovieInfo(): Single<List<MovieInfoList>>

    @Query("DELETE FROM movieInfo")
    fun deleteAllFavoriteMovieInfo(): Completable

    @Query("DELETE FROM movieInfo WHERE title = :movieTitle")
    fun deleteFavoriteMovieInfo(movieTitle: String?): Completable
}