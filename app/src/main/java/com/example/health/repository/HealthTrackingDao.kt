package com.example.health.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.model.DailyTrack

@Dao
interface HealthTrackingDao {

    @Query("SELECT * FROM daily_track ORDER BY id DESC limit 7")
    fun getAllDailyTrack():LiveData<MutableList<DailyTrack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyTrack(dailyTrack: DailyTrack)
}