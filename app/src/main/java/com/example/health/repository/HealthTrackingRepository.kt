package com.example.health.repository

import com.example.health.model.DailyTrack


class HealthTrackingRepository(private val healthTrackingDatabase: HealthTrackingDatabase) {

    suspend fun insertDailyData(dailyTrack: DailyTrack) =
        healthTrackingDatabase.healthTrackingDao().insertDailyTrack(dailyTrack)

    fun getAllDailyData()=healthTrackingDatabase.healthTrackingDao().getAllDailyTrack()
}