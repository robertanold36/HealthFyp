package com.example.health.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.health.model.DailyTrack
import com.example.health.model.DeviceTrackModel
import com.example.health.model.Medicine

@Database(
    entities = [DailyTrack::class, Medicine::class, DeviceTrackModel::class],
    version = 7,
    exportSchema = false
)
abstract class HealthTrackingDatabase : RoomDatabase() {

    abstract fun healthTrackingDao(): HealthTrackingDao

    companion object {
        @Volatile
        private var INSTANCE: HealthTrackingDatabase? = null

        fun getDatabase(context: Context): HealthTrackingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HealthTrackingDatabase::class.java,
                    "health_tracking_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}