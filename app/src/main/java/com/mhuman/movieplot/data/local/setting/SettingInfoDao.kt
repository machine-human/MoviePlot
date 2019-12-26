package com.mhuman.movieplot.data.local.setting

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mhuman.movieplot.network.model.SettingInfoResult
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SettingInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSettingInfo(movieInfoResult: List<SettingInfoResult>): Completable

    @Query("SELECT * FROM settingInfo")
    fun getSettingInfo(): Single<List<SettingInfoResult>>

}