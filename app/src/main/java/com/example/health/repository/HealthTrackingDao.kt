package com.example.health.repository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.health.model.DailyTrack
import com.example.health.model.DeviceTrackModel
import com.example.health.model.Medicine

@Dao
interface HealthTrackingDao {

    @Query("SELECT * FROM daily_track ORDER BY id DESC limit 7")
    fun getAllDailyTrack():LiveData<MutableList<DailyTrack>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyTrack(dailyTrack: DailyTrack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyDeviceTrack(deviceTrackModel: DeviceTrackModel)

    @Query("SELECT * FROM device_track WHERE meals=:mealsTime ORDER BY id ASC")
    fun getAllDeviceDailyTrack(mealsTime:String):LiveData<MutableList<DeviceTrackModel>>

    @Query("SELECT * FROM device_track ORDER BY id DESC limit 30")
    fun getAllDeviceDailyTrackByMonth():LiveData<MutableList<DeviceTrackModel>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedicineData(medicine: Medicine)

    @Query("SELECT * FROM medicine ORDER BY id DESC")
    fun getAllMedicineData():LiveData<MutableList<Medicine>>
}