package com.example.health.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_track")
data class DailyTrack(
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "state") val state: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}